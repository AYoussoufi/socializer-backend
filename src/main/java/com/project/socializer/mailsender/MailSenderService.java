package com.project.socializer.mailsender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class MailSenderService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(String to, String subject,Integer pin) throws MessagingException {
        Map<String, Object> model = new HashMap<>();
        model.put("pin",pin);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);

        String body = templateEngine.process("PinCodeMailTemplate.html", new Context(Locale.getDefault(), model));
        helper.setText(body, true);

        javaMailSender.send(message);
    }
}
