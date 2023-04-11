package co.develhope.websocket2.controller;

import co.develhope.websocket2.entities.ClientMessageDTO;
import co.develhope.websocket2.entities.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
@Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/broadcast")
    public ResponseEntity broadcastMessage(@RequestBody MessageDTO messageDTO){
        simpMessagingTemplate.convertAndSend("/topic/broadcast",messageDTO);
        return ResponseEntity.accepted().body("Messaggio " + messageDTO.getMessage() + " inviato al /topic/broadcast");
    }

    @MessageMapping("/client-message")
    @SendTo("/topic/broadcast")
    public MessageDTO sendMessageNewUser(ClientMessageDTO clientMessageDTO){
        return new MessageDTO(clientMessageDTO.getClientName(), clientMessageDTO.getClientAlert(), clientMessageDTO.getClientMsg());
    }

}