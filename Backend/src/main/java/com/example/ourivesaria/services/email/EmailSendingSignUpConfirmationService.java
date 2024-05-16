package com.example.ourivesaria.services.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmailSendingSignUpConfirmationService extends AbstractEmailSendingServiceImpl {


    @Override
    public void sendEmail(String to, String subject, String body) {
        super.sendEmail(to, subject, body);
    }
}
