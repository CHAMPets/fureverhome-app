package com.champets.fureverhome.application.service.impl;

import com.champets.fureverhome.application.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String messageBody) {
        String htmlContent = messageBody + generateHtmlContent();

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("fureverhome.app@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
            System.out.println("HTML email sent successfully!");
        } catch (MessagingException e) {
            System.out.println("Failed to send HTML email: " + e.getMessage());
        }
    }

    private String generateHtmlContent() {
        return "<br/><link rel=\"preconnect\" href=\"https://fonts.googleapis.com\"/>\n"
                + "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin/>\n"
                + "    <link href=\"https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@100;200;300;400;500;600;700;800;900&amp;display=swap\" rel=\"stylesheet\"/>"
                + "<section th:fragment=\"call-to-action\" xmlns:th=\"http://www.thymeleaf.org\" style=\"padding-top: 3rem !important; padding-bottom: 3rem !important; background: linear-gradient(135deg, #a1cdf1 0%, #D4A0A7 100%); color: white;\">\n"
                + "    <div style=\"width: 100%; padding-right: calc(1.5rem * 0.5); padding-left: calc(1.5rem * 0.5); margin-right: auto; margin-left: auto;\">\n"
                + "        <div style=\"text-align: center;\">\n"
                + "            <h2 style=\"font-size: calc(1.475rem + 2.7vw); font-weight: 300; line-height: 1.2; margin-bottom: 1.5rem !important;\" class=\"fw-bolder\">Looking for a fur-fect companion?</h2>\n"
                + "<a href=\"http://localhost:8080/about\" style=\"display: inline-block; text-decoration: none; padding: 1rem 3rem; font-size: 1rem !important; color: #f8f9fa; background-color: transparent; border: 1px solid #f8f9fa; border-radius: 0.375rem;\" th:href=\"@{/about}\" class=\"btn btn-outline-light btn-lg fs-6 fw-bolder\" onmouseover=\"this.style.backgroundColor = '#f8f9fa'\" onmouseout=\"this.style.backgroundColor = 'transparent'\">Contact us</a>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "</section>\n";
    }

}
