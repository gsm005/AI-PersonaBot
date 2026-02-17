package com.example.java_backend.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.example.java_backend.models.ChatData;
import com.example.java_backend.repository.chatDataRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WhatsAppSenderService {

    private final chatDataRepository chatDataRepo;

    public void sendMessage(String to, String body, String response,Instant incomingTimeStamp) {
        String from=System.getenv("TWILIO_WHATSAPP_NUMBER");
        
        // Ensure both numbers have whatsapp: prefix for WhatsApp channel
        String toNumber = formatWhatsAppNumber(to);
        String fromNumber = formatWhatsAppNumber(from);
        
        Message.creator(
            new PhoneNumber(toNumber),
            new PhoneNumber(fromNumber),
            response
        ).create();
        Instant responseTimeStamp = java.time.Instant.now();
        saveChatData(fromNumber, toNumber, body, response, incomingTimeStamp, responseTimeStamp);
    }
    
    private String formatWhatsAppNumber(String number) {
        if (number == null || number.trim().isEmpty()) {
            return number;
        }
        String cleaned = number.replace("whatsapp:", "").trim();
        return "whatsapp:" + cleaned;
    }

    private void saveChatData(String from, String to, String incomingMessage, String responseMessage,Instant incomingTimestamp,Instant responseTimestamp) {
        ChatData chatData = new ChatData();
        chatData.setFrom(from);
        chatData.setTo(to);
        chatData.setIncomingMessage(incomingMessage);
        chatData.setResponseMessage(responseMessage);
        chatData.setIncomingTimestamp(incomingTimestamp);
        chatData.setResponseTimestamp(responseTimestamp);
        chatDataRepo.save(chatData);
    }
}
