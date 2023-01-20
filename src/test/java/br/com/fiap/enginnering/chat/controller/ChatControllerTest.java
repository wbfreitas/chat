package br.com.fiap.enginnering.chat.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.fiap.enginnering.chat.documents.ChatItem;
import br.com.fiap.enginnering.chat.model.PersonModel;
import br.com.fiap.enginnering.chat.service.ChatService;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ChatControllerTest {

    @InjectMocks
    private ChatController chatController;

    @Mock
    private ChatService service;

    @Test
    public void healthTest() {
        String result = chatController.heath();
        assertEquals("healthy", result);
    }

    @Test
    public void saveTest() {
        ChatItem chatItem = new ChatItem();
        String token = "abcde";
        when(service.save(chatItem, token)).thenReturn(chatItem);
        ChatItem result = chatController.save(chatItem, token);
        assertEquals(chatItem, result);
        verify(service, times(1)).save(chatItem, token);
    }

    @Test
    public void getChatsTest() {
        String token = "abcde";
        List<PersonModel> chatList = new ArrayList<>();
        when(service.getChats(token)).thenReturn(chatList);
        Flux<List<PersonModel>> result = chatController.chats(token);
        StepVerifier.create(result)
                .expectNext(chatList);
    }

    @Test
    public void getMessagesTest() throws InterruptedException {
        String idParceiro = "12345";
        String token = "abcde";
        List<ChatItem> chatList = new ArrayList<>();
        Flux<List<ChatItem>> result = chatController.mensageByPersonId(idParceiro, token);
        StepVerifier.create(result)
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(5))
                .expectNextCount(0)
                .expectNext(chatList);
        when(service.getMessages(idParceiro, token)).thenReturn(chatList);
    }


}