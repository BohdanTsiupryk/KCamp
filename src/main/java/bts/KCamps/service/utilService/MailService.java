package bts.KCamps.service.utilService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private static String username;

    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    public void sendOrderToClient(String emailTo, Map<String, String> data) {
        StringBuilder message = new StringBuilder("\tДякуємо за те що вибрали KidCamps =)\nВаше замовлення:\n");
        message.append(String.format("\t%s - %s\n","camp name", data.get("camp name")));
        message.append(String.format("\t%s - %s\n","user name", data.get("user name")));
        message.append(String.format("\t%s - %s\n","begin date", data.get("begin date")));
        message.append(String.format("\t%s - %s\n","end date", data.get("end date")));

        for (String key : data.keySet()) {
            message.append(String.format("\t%s - %s\n", key, data.get(key)));
        }

        send(emailTo, "KidCamp замовлення", message.toString());
    }
}
