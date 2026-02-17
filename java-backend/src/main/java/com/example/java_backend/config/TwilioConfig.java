package com.example.java_backend.config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    @PostConstruct
    public void init() {
        String accountSid = System.getenv("TWILIO_ACCOUNT_SID");
        String authToken = System.getenv("TWILIO_AUTH_TOKEN");
        Twilio.init(accountSid, authToken);
    }
}