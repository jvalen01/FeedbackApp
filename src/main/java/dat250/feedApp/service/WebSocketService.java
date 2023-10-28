package dat250.feedApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    private final SimpMessagingTemplate template;

    @Autowired
    public WebSocketService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void sendToTopic(String message) {
        template.convertAndSend("/topic/message", message);
    }
    public void sendMessage(String message) {
        this.sendToTopic(message);
    }
}
