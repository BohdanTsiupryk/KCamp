package bts.KCamps.controllers;

import bts.KCamps.repository.UserRepo;
import bts.KCamps.enums.Role;
import bts.KCamps.model.ModeratorRequest;
import bts.KCamps.model.User;
import bts.KCamps.repository.RequestRepo;
import bts.KCamps.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final RequestRepo requestRepo;

    @GetMapping("/request/delete/{req}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteReq(@PathVariable ModeratorRequest req) {
        requestRepo.delete(req);
        return "redirect:/request";
    }

    @GetMapping("/request")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getRequests(Model model) {
        Iterable<ModeratorRequest> all = requestRepo.findAll();
        model.addAttribute("requests", all);
        model.addAttribute("hasReq", all.iterator().hasNext());
        return "requests";
    }

    @GetMapping("/user/delete/{user}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable Long user) {
        userService.removeUser(user);
        return "redirect:/user";
    }

    @PostMapping("/modrequest")
    @PreAuthorize("hasAuthority('USER')")
    public String modRequest(
            @AuthenticationPrincipal User currentUser,
            @RequestParam Map<String, String> form,
            Model model
            ) {
        ModeratorRequest mr = new ModeratorRequest();
        mr.setAuthor(currentUser);
        mr.setCampName(form.get("campName"));
        mr.setFullName(form.get("fullName"));
        mr.setCampUrl(form.get("campUrl"));
        mr.setMessage(form.get("message"));

        requestRepo.save(mr);

        model.addAttribute("modReq", true);

        return "redirect:/profile";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userList(@AuthenticationPrincipal User user, Model model) {
        if (user.getRole().contains(Role.ADMIN)) {
            Iterable<User> all = userService.getAll();
            model.addAttribute("users", all);
            return "userList";
        }
        return "redirect:/profile";
    }
}
