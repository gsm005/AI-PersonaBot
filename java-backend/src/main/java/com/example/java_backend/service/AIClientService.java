package com.example.java_backend.service;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AIClientService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAIResponse(String message) {

        String url = "http://localhost:8000/chat";

        // Create JSON body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("message", message);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity =
                new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response =
                    restTemplate.postForEntity(url, entity, Map.class);

            Map responseBody = response.getBody();

            if (responseBody != null && responseBody.containsKey("response")) {
                return responseBody.get("response").toString();
            }

            return "AI service returned empty response.";

        } catch (Exception e) {
            e.printStackTrace();
            return "AI service error occurred.";
        }
    }
}