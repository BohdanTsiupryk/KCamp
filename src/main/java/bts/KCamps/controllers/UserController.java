package bts.KCamps.controllers;

import bts.KCamps.enums.Role;
import bts.KCamps.model.User;
import bts.KCamps.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("orders")
    @Transactional
    public String getUserOrders(@AuthenticationPrincipal User user, Model model) {
        User byId = userService.findById(user.getId());
        model.addAttribute("orders", byId.getBoughtTrips());

        return "orderList";
    }

    @GetMapping("editor/{userId}")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public String userEditForm(
            @AuthenticationPrincipal User sessionUser,
            @PathVariable Long userId,
            Model model
    ) {
        User user = userService.findById(userId);
        if ((sessionUser.getRole().contains(Role.USER)
                && user.getId() == sessionUser.getId())
                || sessionUser.getRole().contains(Role.ADMIN)) {
            model.addAttribute("user", user);
            model.addAttribute("roles", Role.values());
            return "userEdit";
        }
        return "redirect:/profile";
    }

    @PostMapping
    public String userUpdate(
            @AuthenticationPrincipal User currentUser,
            @RequestParam("id") User user,
            @RequestParam Map<String, String> form,
            Model model) {
        if (currentUser.getRole().contains(Role.ADMIN) || currentUser.getId() == user.getId()) {
            userService.updateUser(user, form);
            if (currentUser.getRole().contains(Role.USER)) {
                return "redirect:/profile";
            }
            return "redirect:/user";
        }
        model.addAttribute("statusCode", "403");
        model.addAttribute("exception", "Forbidden");
        return "error";
    }
}
