package br.com.fiap.enginnering.chat.controller;

import br.com.fiap.enginnering.chat.documents.ChatItem;
import br.com.fiap.enginnering.chat.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    ChatService service;

    @GetMapping("/health")
    public String heath() {
        return "healthy";
    }

    @PostMapping
    public ChatItem save(@Valid @RequestBody  ChatItem chat) {
        return service.save(chat);
    }

    @GetMapping
    public List<ChatItem> conversation(@Validated @RequestParam("id_receptor") String idReceptor,
                                       @Validated @RequestParam("id_sender") String idSender) {
        return service.conversation(idReceptor, idSender);
    }
}
