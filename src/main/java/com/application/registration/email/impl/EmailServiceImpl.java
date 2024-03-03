package com.application.registration.email.impl;

import com.application.registration.email.EmailService;
import com.application.registration.exception.ConfirmationEmailException;
import freemarker.template.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    Configuration configuration;

    @Value("${sender.email}")
    String email;

    @Async
    @Override
    public void send(String to, Map<String, Object> models) {
        try {
            if(to.equals(email))
                throw new MessagingException();

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "utf-8");
            Template template = configuration.getTemplate("email.html");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, models);
            helper.setText(html, true);
            helper.addInline("biglogo.jpg", new ClassPathResource("./img/biglogo.jpg"));
            helper.setFrom(email);
            helper.setTo(to);
            helper.setSubject("Email confirmation");
            javaMailSender.send(message);
        } catch (MessagingException | IOException | TemplateException e) {
            log.error(e.getMessage());
            throw new ConfirmationEmailException("Failed to send mail!");
        }
    }
}
