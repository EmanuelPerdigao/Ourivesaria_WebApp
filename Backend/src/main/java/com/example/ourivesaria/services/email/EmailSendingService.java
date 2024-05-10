package com.example.ourivesaria.services.email;

public interface EmailSendingService {

    void sendEmail(String to, String subject, String body);
}
