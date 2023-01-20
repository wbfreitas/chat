package br.com.fiap.enginnering.chat.controller;

import br.com.fiap.enginnering.chat.documents.ChatItem;
import br.com.fiap.enginnering.chat.model.PersonModel;
import br.com.fiap.enginnering.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import java.time.Duration;
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
    public ChatItem save(@RequestBody @Valid ChatItem chat, @RequestHeader (name="Authorization") String token) {
        return service.save(chat, token);
    }


    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<List<PersonModel>>chats(@RequestHeader (name="Authorization") String token) {
        return Flux.interval(Duration.ofSeconds(5)).map(
                __-> service.getChats(token)
        );
    }

    @GetMapping(value = "/mensagens-parceiros/{idParceiro}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<List<ChatItem>> mensageByPersonId(@PathVariable String idParceiro, @RequestHeader (name="Authorization") String token) {
        return Flux.interval(Duration.ofSeconds(5)).map(
                __-> service.getMessages(idParceiro, token)
        );
    }
}