package com.athletecoach.athletecoach_ai.security;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificationPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendPlanReadyNotification(String email,
                                          Long planId, Integer totalDrills) {

        Map<String, Object> notification = new HashMap<>();
        notification.put("type", "PLAN_READY");
        notification.put("planId", planId);
        notification.put("totalDrills", totalDrills);
        notification.put("message", "Your personalized training plan is ready!");

        messagingTemplate.convertAndSendToUser(
                email,
                "/queue/notifications",
                notification
        );
    }

    public void sendDrillCompletedNotification(String email,
                                               String drillName) {

        Map<String, Object> notification = new HashMap<>();
        notification.put("type", "DRILL_COMPLETED");
        notification.put("drillName", drillName);
        notification.put("message", "Great job completing " + drillName + "!");

        messagingTemplate.convertAndSendToUser(
                email,
                "/queue/notifications",
                notification
        );
    }

    public void sendLevelUpNotification(String email, String newLevel) {

        Map<String, Object> notification = new HashMap<>();
        notification.put("type", "LEVEL_UP");
        notification.put("newLevel", newLevel);
        notification.put("message", "You levelled up to " + newLevel + "!");

        messagingTemplate.convertAndSendToUser(
                email,
                "/queue/notifications",
                notification
        );
    }
}