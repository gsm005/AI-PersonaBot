package com.example.java_backend.controller;
import java.time.Instant;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_backend.service.MessageOrchestratorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/webhook")
public class WhatsappWebhookController {

    private final MessageOrchestratorService messageOrchestratorService;
    
    @PostMapping(
        value = "/whatsapp",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public ResponseEntity<String> handleWebhook(
            @RequestParam("From") String from,
            @RequestParam("Body") String body
    ) {
        Instant incomingTimeStamp=Instant.now();
        messageOrchestratorService.orchestrateMessage(from, body, incomingTimeStamp);
        return ResponseEntity.ok("Webhook received");
    }
}