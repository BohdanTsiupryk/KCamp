package bts.KCamps.controllers;

import bts.KCamps.model.User;
import bts.KCamps.service.MailService;
import bts.KCamps.service.UserService;
import bts.KCamps.util.ControllerUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final MailService mailService;

    public RegistrationController(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @GetMapping("/registration")
    public String getRegForm() {
        //TODO дописати форму для заповнення адереси та інфо, без неї не пускати
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String password2,
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {
        boolean passConfrim = !user.getPassword().equals(password2);
        boolean phoneConfrim = !user.getPhoneNumber().matches("\\+?(\\d\\d)?((\\d){10})");
        boolean passPower = !user.getPassword().matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*");
        if (passPower) {
            model.addAttribute("passPowerError", "Заслабкий пароль. Повинен містити: <br>- одну цифру,<br>- одну букву нижнього та верхнього регістрів" +
                    "<br>- мінімум одни спец символ<br>- мінімальна довжина 8 символів");
        }
        if (phoneConfrim) {
            model.addAttribute("phoneNumberError", "Неправильний формат номеру (+380*********, 0*********)");
        }
        if (passConfrim) {
            model.addAttribute("password2Error", "Паролі не збігаються");
        }
        if (bindingResult.hasErrors() || passConfrim || phoneConfrim || passPower) {
            Map<String, String> errorMap = ControllerUtil.getErrorMap(bindingResult);
            model.mergeAttributes(errorMap);
            model.addAttribute("user", user);

            return "registration";
        } else {
            String code = UUID.randomUUID().toString();
            user.setActivationCode(code);

            if (userService.addUser(user)) {
                model.addAttribute("message", "Успішно зареєстровано! Будь ласка, перегляньте свою пошту для активації акаунту!");
                model.addAttribute("alert", "success");

                mailService.send(user.getEmail(), "Activation Code",
                        "Ласкаво просимо у Kid Camps! Ваша url для активації -  http://localhost:8080/activation/" + code);
            } else {
                model.addAttribute("message", "User email EXIST");
                return "registration";
            }
        }
        return "login";
    }
}
