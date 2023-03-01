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

    public void sendEmailToVerifyEmail(String to, String subject, Integer pin)   {
        try{
        Map<String, Object> model = new HashMap<>();
        model.put("pin",pin);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);

        String body = templateEngine.process("PinCodeMailTemplate.html", new Context(Locale.getDefault(), model));
        helper.setText(body, true);
        javaMailSender.send(message);
        }catch (MessagingException e){
            throw new RuntimeException(e.getMessage());
        }

    }

    public void sendEmailToVerifyPasswordRecovery(String to, String subject,String url)   {
        try{
            Map<String, Object> model = new HashMap<>();
            model.put("url",url);
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);

            String body = templateEngine.process("RecoverPassword.html", new Context(Locale.getDefault(), model));
            helper.setText(body, true);
            javaMailSender.send(message);
        }catch (MessagingException e){
            throw new RuntimeException(e.getMessage());
        }

    }
}
