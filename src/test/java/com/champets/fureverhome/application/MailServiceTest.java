package com.champets.fureverhome.application;

import com.champets.fureverhome.application.service.impl.MailServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MailServiceTest {
    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private MailServiceImpl mailService;

    @Test
    public void testSendEmail() throws MessagingException {
        String to = "test@example.com";
        String subject = "Test Subject";
        String messageBody = "Test Message Body";

        MimeMessage mimeMessageMock = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessageMock);

        mailService.sendEmail(to, subject, messageBody);

        verify(mailSender).send(mimeMessageMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendEmail_NullTo() throws MessagingException {
        String to = null;
        String subject = "Test Subject";
        String messageBody = "Test Message Body";

        MimeMessage mimeMessageMock = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessageMock);

        mailService.sendEmail(to, subject, messageBody);
    }

    @Test
    public void testSendEmail_EmptySubject() throws MessagingException {
        String to = "test@example.com";
        String subject = "";
        String messageBody = "Test Message Body";

        MimeMessage mimeMessageMock = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessageMock);

        mailService.sendEmail(to, subject, messageBody);

        verify(mailSender).send(any(MimeMessage.class));
    }

}
