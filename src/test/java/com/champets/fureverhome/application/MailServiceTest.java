package com.champets.fureverhome.application;

import com.champets.fureverhome.application.service.impl.MailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;

import static org.mockito.Mockito.*;

public class MailServiceTest {

    @Mock
    private JavaMailSender mockMailSender;

    @InjectMocks
    private MailServiceImpl mailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendEmail() throws MessagingException {
        // Arrange
        String to = "test@example.com";
        String subject = "Test Subject";
        String messageBody = "Test Message";

        // Act
        mailService.sendEmail(to, subject, messageBody);

        // Assert
        verify(mockMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testSendEmail_NullSubject() throws MessagingException {
        // Arrange
        String recipient = "test@example.com";
        String subject = null;
        String messageBody = "Test Message";

        // Act
        mailService.sendEmail(recipient, subject, messageBody);

        // Assert
        // Verify that the mailSender.send method is called once
        verify(mockMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testSendEmail_NullMessageBody() throws MessagingException {
        // Arrange
        String recipient = "test@example.com";
        String subject = "Test Subject";
        String messageBody = null;

        // Act
        mailService.sendEmail(recipient, subject, messageBody);

        // Assert
        // Verify that the mailSender.send method is called once
        verify(mockMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

}
