package bts.KCamps.controllers;

import bts.KCamps.model.Camp;
import bts.KCamps.model.CampChange;
import bts.KCamps.model.Child;
import bts.KCamps.model.User;
import bts.KCamps.service.CampService;
import bts.KCamps.service.ChangeService;
import bts.KCamps.service.UserService;
import bts.KCamps.service.utilService.MailService;
import bts.KCamps.util.RandomGenerator;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PurposeController {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final MailService mailService;
    private final UserService userService;
    private final CampService campService;
    private final ChangeService changeService;

    @GetMapping("/buy/{camp}/{change}")
    public String getBuyPage(
            @AuthenticationPrincipal User user,
            @PathVariable("change") CampChange change,
            @PathVariable("camp") Camp camp,
            Model model) {
        if (!user.getActivationCode().equals("")) {
            model.addAttribute("message", "Для здійснення покупок, потрібно підтвердити електронну пошту!");
            return "messagePage";
        }
        User byId = userService.findById(user.getId());

        model.addAttribute("camp", camp);
        model.addAttribute("change", change);
        model.addAttribute("user", byId);
        return "buyPage";
    }

    @PostMapping("/purpose")
    public String buyRequest(
            @AuthenticationPrincipal User user,
            @RequestParam Long campId,
            @RequestParam Long changeId,
            @RequestParam Map<String, String> form,
            Model model) {
        Camp camp = campService.getById(campId);
        CampChange change = changeService.getById(changeId);

        Map<String, String> data = buildDataMap(form, camp, user, change);

        Child child = new Child(
                form.get("kidFullName"),
                form.get("kidDocument"),
                LocalDate.parse(form.get("kidBirthday"), formatter),
                form.get("kidCitizenship"),
                user.getId(),
                form.get("kidSpecialWishes"));

        String orderId = RandomGenerator.randomOrderId();

        userService.addOrder(change, child, user, orderId);
        mailService.sendOrderToClient(user.getEmail(), data);

        model.addAttribute("message", "Дякуємо за замовлення, згодом представник табору звяжеться з вами" +
                "<br> Після оплати путівка буде відправлена вам на пошту вказану у профілі");
        model.mergeAttributes(data);
        return "messagePage";
    }

    private Map<String, String> buildDataMap(Map<String, String> form, Camp camp, User user, CampChange change) {
        Map<String, String> data = new HashMap<>();
        data.put("camp_name", camp.getNameCamp());
        data.put("user_name", user.getUsername());
        data.put("begin_date", change.getBeginDate().toString());
        data.put("end_date", change.getEndDate().toString());

        form.keySet().stream()
                .filter(s -> s.startsWith("kid"))
                .forEach(s -> data.put(s, form.get(s)));
        return data;
    }

}
