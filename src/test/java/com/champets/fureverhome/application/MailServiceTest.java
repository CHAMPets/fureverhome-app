package com.champets.fureverhome.application;

import com.champets.fureverhome.application.service.impl.MailServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.*;

// ...

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

        // Create a mock of the MimeMessage object
        MimeMessage mimeMessageMock = mock(MimeMessage.class);

        // Create a mock of the MimeMessageHelper object
        MimeMessageHelper messageHelperMock = mock(MimeMessageHelper.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessageMock);


        // Perform the test
        mailService.sendEmail(to, subject, messageBody);

        // Verify that the mailSender.send() method was called
        verify(mailSender).send(mimeMessageMock);

        // Add additional verification if required
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendEmail_NullTo() throws MessagingException {
        String to = null;
        String subject = "Test Subject";
        String messageBody = "Test Message Body";


        MimeMessage mimeMessageMock = mock(MimeMessage.class);

        // Create a mock of the MimeMessageHelper object
        MimeMessageHelper messageHelperMock = mock(MimeMessageHelper.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessageMock);



        // Perform the test
            mailService.sendEmail(to, subject, messageBody);

    }

    @Test
    public void testSendEmail_EmptySubject() throws MessagingException {
        String to = "test@example.com";
        String subject = "";
        String messageBody = "Test Message Body";

        MimeMessage mimeMessageMock = mock(MimeMessage.class);

        // Create a mock of the MimeMessageHelper object
        MimeMessageHelper messageHelperMock = mock(MimeMessageHelper.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessageMock);
        doAnswer(invocation -> {
            // Get the argument passed to the send() method
            MimeMessage messageArg = invocation.getArgument(0);

            // Assert the properties of the messageArg as needed

            // Return null to mimic the behavior of the real send() method
            return null;
        }).when(mailSender).send(any(MimeMessage.class));
        // Perform the test
        mailService.sendEmail(to, subject, messageBody);


        // Verify that the mailSender.send() method was called
        verify(mailSender).send(any(MimeMessage.class));

        // Add additional verification if required
    }

}
