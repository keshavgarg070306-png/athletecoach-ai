package com.athletecoach.athletecoach_ai.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WebSocketController {

    @MessageMapping("/ping")
    @SendTo("/topic/pong")
    public Map<String, String> ping(Map<String, String> message) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "connected");
        response.put("message", "WebSocket is working!");
        return response;
    }
}