package com.example.ourivesaria.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailSendingServiceImpl implements EmailSendingService {

    @Autowired
    private JavaMailSender emailSender;
    @Override
    public void sendEmail(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("te994361@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        try {
            emailSender.send(message);
        }catch (MailException e) {
            e.printStackTrace();
        }


    }
}
