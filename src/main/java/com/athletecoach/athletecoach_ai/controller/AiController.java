package com.athletecoach.athletecoach_ai.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:5173")
public class AiController {

    private final String GEMINI_KEY = "AIzaSyBIOo3T64hVFmYFvkZ5HYUYtNh9dz3VUqY";
    private final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash-lite:generateContent?key=" + GEMINI_KEY;

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> req) {
        try {
            WebClient client = WebClient.create();
            Map<String, Object> body = Map.of(
                    "contents", List.of(Map.of(
                            "parts", List.of(Map.of("text", req.get("prompt")))
                    ))
            );
            Map response = client.post()
                    .uri(GEMINI_URL)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            List candidates = (List) response.get("candidates");
            Map first = (Map) candidates.get(0);
            Map content = (Map) first.get("content");
            List parts = (List) content.get("parts");
            Map part = (Map) parts.get(0);
            String text = (String) part.get("text");

            return Map.of("response", text);
        } catch (Exception e) {
            return Map.of("response", "AI temporarily unavailable. Please try again!");
        }
    }
}