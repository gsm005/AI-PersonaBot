package com.example.java_backend.service;

import java.time.Instant;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageOrchestratorService {

    private final WhatsAppSenderService whatsAppSenderService;
    private final AIClientService aiClientService;
    
    @Async
    public void orchestrateMessage(String to,String incomingMessage,Instant incomingTimeStamp) {
        String resp;
        if(incomingMessage!=null && !incomingMessage.trim().isEmpty() && incomingMessage.length()<=500 && isSafe(incomingMessage)){
            resp=aiClientService.getAIResponse(incomingMessage);
            whatsAppSenderService.sendMessage(to,incomingMessage,resp,incomingTimeStamp);
        }   
        else{
            resp="Sorry, your message was flagged as unsafe and cannot be processed without human review.";
            whatsAppSenderService.sendMessage(to,incomingMessage,resp,incomingTimeStamp);
        }
        return;
    }

    boolean isSafe(String message){
        // Implement your safety checks here (e.g., profanity filter, spam detection, etc.)
        // For demonstration, we'll just check for a simple banned word.
        String bannedWord = "badword"; // Replace with actual logic
        return !message.toLowerCase().contains(bannedWord);
    }
}
