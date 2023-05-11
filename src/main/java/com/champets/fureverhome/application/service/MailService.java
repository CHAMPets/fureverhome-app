package com.champets.fureverhome.application.service;

public interface MailService {
    void sendEmail(String to, String subject, String messageBody);
}
