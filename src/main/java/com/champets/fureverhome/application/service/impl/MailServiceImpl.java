package com.champets.fureverhome.application.service.impl;

import com.champets.fureverhome.application.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String messageBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("fureverhome.app@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(messageBody);
        mailSender.send(message);
        System.out.println("Mail sent.");
    }
}
