package com.zosh.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String brevoApiKey;

    public void sendVerificationOtpEmail(
            String userEmail,
            String otp,
            String subject,
            String text) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", brevoApiKey);

        Map<String, Object> requestBody = new HashMap<>();

        Map<String, String> sender = new HashMap<>();
        sender.put("name", "Zosh Bazaar");
        sender.put("email", "vvvishalyou99@gmail.com");

        List<Map<String, String>> to = new ArrayList<>();

        Map<String, String> recipient = new HashMap<>();
        recipient.put("email", userEmail);

        to.add(recipient);

        requestBody.put("sender", sender);
        requestBody.put("to", to);
        requestBody.put("subject", subject);

        requestBody.put(
                "htmlContent",
                "<h2>Zosh Bazaar OTP Verification</h2>"
                        + "<p>Your OTP is : <b>"
                        + otp
                        + "</b></p>"
        );

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        "https://api.brevo.com/v3/smtp/email",
                        entity,
                        String.class
                );

        System.out.println("BREVO RESPONSE : " + response.getBody());
    }
}