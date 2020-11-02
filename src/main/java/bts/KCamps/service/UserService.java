package bts.KCamps.service;

import bts.KCamps.enums.Role;
import bts.KCamps.model.BoughtTrip;
import bts.KCamps.model.CampChange;
import bts.KCamps.model.Child;
import bts.KCamps.model.User;
import bts.KCamps.model.UserAddress;
import bts.KCamps.model.UserInfo;
import bts.KCamps.repository.ChildRepo;
import bts.KCamps.repository.TripRepo;
import bts.KCamps.repository.UserRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LogManager.getLogger(UserService.class);
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final TripRepo tripRepo;
    private final ChildRepo childRepo;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, TripRepo tripRepo, ChildRepo childRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.tripRepo = tripRepo;
        this.childRepo = childRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.findByUsername(s);
    }

    public void addOrder(CampChange change, Child child, User user, String orderId) {
        change.setFreePlace(change.getFreePlace() - 1);
        BoughtTrip trip = new BoughtTrip(change, child, user);
        trip.setOrderId(orderId);
        user.getBoughtTrips()
                .add(trip);
        userRepo.save(user);
    }

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

    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRole(Collections.singleton(Role.USER));
        user.setAddress(new UserAddress());
        user.setUserInfo(new UserInfo(LocalDate.of(2000, Month.JANUARY, 1),
                "null", "ukranian", user));
        userRepo.save(user);
        return true;
    }

    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    public void updateUser(User user, Map<String, String> form) {
        user.setUsername(form.get("username"));
        user.setEmail(form.get("email"));
        user.setPhone(form.get("phone"));
        String password = form.get("password");
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        user.getUserInfo().setBirthday(LocalDate.parse(form.get("birthday"), formatter));
        user.getUserInfo().setCitizenship(form.get("citizenship"));
        user.getUserInfo().setPassportNumber(form.get("passportNumber"));

        user.getAddress().setCity(form.get("city"));
        user.getAddress().setAddress(form.get("address"));

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

        userRepo.save(user);
    }
}
