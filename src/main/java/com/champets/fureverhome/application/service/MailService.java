package com.champets.fureverhome.application.service;

import javax.mail.MessagingException;

public interface MailService {
    void sendEmail(String to, String subject, String messageBody) throws MessagingException;
}
