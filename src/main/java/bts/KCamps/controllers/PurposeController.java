package bts.KCamps.controllers;

import bts.KCamps.model.Camp;
import bts.KCamps.model.CampChange;
import bts.KCamps.model.Child;
import bts.KCamps.model.User;
import bts.KCamps.service.MailService;
import bts.KCamps.service.UserService;
import bts.KCamps.util.RandomGenerator;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PurposeController {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final MailService mailService;
    private final UserService userService;

    public PurposeController(MailService mailService, UserService userService) {
        this.mailService = mailService;
        this.userService = userService;
    }

    @GetMapping("/buy/{camp}/{change}")
    public String getBuyPage(
            @AuthenticationPrincipal User user,
            @PathVariable("change") CampChange change,
            @PathVariable("camp") Camp camp,
            Model model
    ) {
        if (!user.getActivationCode().equals("")) {
            model.addAttribute("message", "Для здійснення покупок, потрібно підтвердити електронну пошту!");
            return "messagePage";
        }


        model.addAttribute("camp", camp);
        model.addAttribute("change", change);
        model.addAttribute("user", user);
        return "buyPage";
    }

    @PostMapping("/purpose")
    public String buyRequest(
            @AuthenticationPrincipal User user,
            @RequestParam("campId") Camp camp,
            @RequestParam("changeId") CampChange change,
            @RequestParam Map<String, String> form,
            Model model
    ) throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("camp name", camp.getNameCamp());
        data.put("user name", user.getUsername());
        data.put("begin date", change.getBeginDate().toString());
        data.put("end date", change.getEndDate().toString());

        for (String line : form.keySet()) {
            if (line.startsWith("kid")) {
                data.put(line, form.get(line));
            }
        }

        Child child = new Child(
                data.get("kidFullName"),
                data.get("kidDocument"),
                LocalDate.parse(data.get("kidBirthday"), formatter),
                data.get("kidCitizenship"),
                user,
                data.get("kidSpecialWishes"));
        user.getChild().add(child);

        String orderId = RandomGenerator.randomOrderId();

        userService.addOrder(change, child, user, orderId);
        mailService.sendOrderToClient(user.getEmail(), data);

        model.addAttribute("message", "Після Оплати, замовлення буде відправлено вам на пошту,\n" +
                " та ви зможете переглянути його у профілі");
        return "messagePage";
    }
}
