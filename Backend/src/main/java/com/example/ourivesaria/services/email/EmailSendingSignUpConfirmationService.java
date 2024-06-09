package com.example.ourivesaria.services.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmailSendingSignUpConfirmationService extends AbstractEmailSendingServiceImpl {

    private final String subject = "Confirmation Email";
    private final String bodyTemplate = "Welcome to Ourivesaria \n Please verify your email by clicking on the link below \n http://localhost:3000/user/verify?register_token=";

    public void sendSignUpConfirmationEmail(String to, String token) {
        String body = bodyTemplate + token;
        sendEmail(to, subject, body);
    }
}
