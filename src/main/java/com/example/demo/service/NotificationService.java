package com.example.demo.service;

import java.util.List;
import java.util.Properties;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class NotificationService {
    private Properties mailAccessCredentials;
    private Session mailSession;
    private MimeMessage mailMessage;

    @Value("${spring.mail.username}")
    private String usernameEmail;
    @Value("${spring.mail.password}")
    private String passwordEmail;

    private void populateProperties() {
        // Step1
        mailAccessCredentials = System.getProperties();
        mailAccessCredentials.put("mail.smtp.port", "587"); // TLS Port
        mailAccessCredentials.put("mail.smtp.auth", "true"); // Enable Authentication
        mailAccessCredentials.put("mail.smtp.starttls.enable", "true"); // Enable StartTLS
    }

    private void populateMailMessage(List<String> to, String subject, String text) throws MessagingException {
        populateProperties();
        // Step2
        mailSession = Session.getDefaultInstance(mailAccessCredentials, null);
        mailMessage = new MimeMessage(mailSession);

        InternetAddress[] address = new InternetAddress[to.size()];
        for (int i = 0; i < to.size(); i++) {
            address[i] = new InternetAddress(to.get(i));
        }

        mailMessage.setRecipients(Message.RecipientType.TO, address);

        mailMessage.setSubject(subject, "UTF-8");

        mailMessage.setContent(text, "text/html; charset=UTF-8");
    }

    @Async
    public void sendMail(List<String> to, String subject, String text) {
        try {
            populateMailMessage(to, subject, text);
            Thread.sleep(40000);
            // Step3
            Transport transport = mailSession.getTransport("smtp");

            // Enter your correct gmail UserID and Password
            transport.connect("smtp.gmail.com", usernameEmail, passwordEmail);
            transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
