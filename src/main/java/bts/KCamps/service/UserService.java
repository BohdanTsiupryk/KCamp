package bts.KCamps.service;

import bts.KCamps.enums.Role;
import bts.KCamps.exception.NotFoundException;
import bts.KCamps.model.BoughtTrip;
import bts.KCamps.model.CampChange;
import bts.KCamps.model.Child;
import bts.KCamps.model.User;
import bts.KCamps.repository.ChangeRepo;
import bts.KCamps.repository.ChildRepo;
import bts.KCamps.repository.TripRepo;
import bts.KCamps.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private static final Logger logger = LogManager.getLogger(UserService.class);
    private final UserRepo userRepo;
    private final TripRepo tripRepo;
    private final ChildRepo childRepo;
    private final ChangeRepo changeRepo;
    private final PasswordEncoder passwordEncoder;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public User findById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id.toString(), User.class));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.findByUsername(s);
    }

    @Transactional
    public void addOrder(CampChange change, Child child, User user, String orderId) {
        change.setFreePlace(change.getFreePlace() - 1);
        BoughtTrip trip = new BoughtTrip(change, child, user);
        trip.setOrderId(orderId);
        childRepo.save(child);
        changeRepo.save(change);
        tripRepo.save(trip);
    }

    @Transactional
    public boolean checkCode(String code) {
        User user = userRepo.findByActivationCode(code);
        if (user != null) {
            user.setActivationCode("");
            userRepo.save(user);
            logger.info("User " + user.getUsername() + " activate account!");
            return true;
        }
        return false;
    }

    @Transactional
    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRole(Collections.singleton(Role.USER));
//        user.setAddress(new UserAddress());
//        user.setUserInfo(new UserInfo(LocalDate.of(2000, Month.JANUARY, 1), "null", "ukranian", user));
        userRepo.save(user);
        return true;
    }

    @Transactional
    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    @Transactional
    public void updateUser(User user, Map<String, String> form) {
        user.setUsername(form.get("username"));
        user.setEmail(form.get("email"));
        user.setPhone(form.get("phone"));
        String password = form.get("password");
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        user.setBirthday(LocalDate.parse(form.get("birthday"), formatter));
        user.setCitizenship(form.get("citizenship"));
        user.setPassportNumber(form.get("passportNumber"));

        user.setCity(form.get("city"));
        user.setAddress(form.get("address"));

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRole().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRole().add(Role.valueOf(key));
            }
        }

        if (user.getRole().isEmpty()) {
            user.getRole().add(Role.USER);
        }

        userRepo.saveAndFlush(user);
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public void removeUser(Long user) {
        User byId = userRepo.findById(user)
                .orElseThrow(() -> new NotFoundException(user.toString(), User.class));
        userRepo.delete(byId);
    }
}
