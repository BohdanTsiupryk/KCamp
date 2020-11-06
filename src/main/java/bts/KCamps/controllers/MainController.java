package bts.KCamps.controllers;

import bts.KCamps.repository.CampRepo;
import bts.KCamps.repository.UserRepo;
import bts.KCamps.enums.Childhood;
import bts.KCamps.enums.Interesting;
import bts.KCamps.enums.Location;
import bts.KCamps.model.Camp;
import bts.KCamps.model.User;
import bts.KCamps.service.impl.CampServiceImpl;
import bts.KCamps.service.UserService;
import bts.KCamps.util.ControllerUtil;
import bts.KCamps.util.EnumUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {
    private static final Logger logger = LogManager.getLogger(MainController.class);

    private final CampRepo campRepo;
    private final UserRepo userRepo;
    private final CampServiceImpl campService;
    private final UserService userService;

    public MainController(CampRepo campRepo, UserRepo userRepo, CampServiceImpl campService, UserService userService) {
        this.campRepo = campRepo;
        this.userRepo = userRepo;
        this.campService = campService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("message", "Користувача з такою комбінацією логін/пароль не знайдено");
            model.addAttribute("alert", "danger");
        }

        if (logout != null) {
            model.addAttribute("message", "Ви успішно вийшли з системи");
            model.addAttribute("alert", "success");
        }

        return "login";
    }

    @GetMapping("/about")
    public String getAbout() {
        return "about";
    }

    @GetMapping("/")
    public String getMain(Model model) {
        model.addAttribute("camps", campRepo.findAll());
        ControllerUtil.addTags(model);
        return "index";
    }

    @GetMapping("/sort")
    public String sort(Model model) {
        model.addAttribute("camps", campService.findSortedByRating());
        ControllerUtil.addTags(model);
        return "index";
    }

    @GetMapping("/filter/{from}/{criteria}")
    public String filter(
            @PathVariable String criteria,
            @PathVariable String from,
            Model model) {
        Iterable<Camp> result;
        if (from.equals("loc")) {
            result = campService.findByTag(Location.getByDescription(criteria), null, null);
        } else if (from.equals("inter")) {
            result = campService.findByTag(null, Interesting.getByDescription(criteria), null);
        } else if (from.equals("hood")) {
            result = campService.findByTag(null, null, Childhood.valueOf(criteria));
        } else {
            result = campRepo.findAll();
        }
        model.addAttribute("camps", result);
        ControllerUtil.addTags(model);
        return "index";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam(value = "interests", required = false) String[] interests,
                         @RequestParam(value = "locations", required = false) String[] locations,
                         @RequestParam(value = "childhoods", required = false) String[] childhoods,
                         Model model) {

        Set<Interesting> interestingSet = interests == null ? new HashSet<>() : EnumUtil.getInterests(interests);
        Set<Location> locationSet = locations == null ? new HashSet<>() : EnumUtil.getLocations(locations);
        Set<Childhood> childhoodSet = childhoods == null ? new HashSet<>() : EnumUtil.getChildhoods(childhoods);

        Iterable<Camp> byTags;
        if (interests == null && locations == null) {
            byTags = campRepo.findAll();
        } else {
            byTags = campService.findByTags(locationSet, interestingSet, childhoodSet);
        }

        model.addAttribute("camps", byTags);
        ControllerUtil.addTags(model);
        return "index";
    }

    @GetMapping("/moderatorFrom")
    public String getModeratorForm() {
        return "modform";
    }

    @GetMapping("/profile")
    public String profile(
            @AuthenticationPrincipal User user,
            Model model) {
        List<Camp> userCamps = campRepo.findAllByAuthor(user);

        model.addAttribute("userFormDb", user);
        model.addAttribute("campError", false);
        model.addAttribute("userCamps", userCamps);
        ControllerUtil.addTags(model);
        return "profile";
    }

    @GetMapping("/activation/{code}")
    public String activation(@PathVariable String code, Model model) {
        if (userService.checkCode(code)) {
            model.addAttribute("message", "Успішно активовано!");
            model.addAttribute("alert", "success");
        } else {
            model.addAttribute("message", "Вже активовано!");
            model.addAttribute("alert", "success");
        }

        return "login";
    }
}
