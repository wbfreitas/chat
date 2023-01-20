package br.com.fiap.enginnering.chat.service;


import br.com.fiap.enginnering.chat.documents.ChatItem;
import br.com.fiap.enginnering.chat.model.PersonModel;
import br.com.fiap.enginnering.chat.repository.ChatRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class ChatServiceTest {
    @InjectMocks
    ChatService chatService;

    @Mock
    ChatRepository repository;

    @Mock
    ClientGateway clientGateway;

    @Test
    public void testSave() {
        String token = "123456789";
        ChatItem item = new ChatItem();
        item.setTalkerID("1");
        item.setReceiverID("2");
        PersonModel person = mockPerson();
        when(repository.save(any(ChatItem.class))).thenReturn(new ChatItem());
        when(clientGateway.getSessionId(token)).thenReturn(Optional.of(person));

        ChatItem savedChatItem = chatService.save(item, token);
        assertNotNull(savedChatItem);
    }

    @Test
    public void testGetChats() {
        String token = "123456789";
        PersonModel person = mockPerson();
        List<String> conversationIDs = Arrays.asList("/123/456", "/123/789");
        List<PersonModel> expectedPersonModels = Arrays.asList(
                mockPerson(),
                mockPerson()
        );
        when(clientGateway.getSessionId(token)).thenReturn(Optional.of(person));
        when(repository.findConversationIDs(person.getId())).thenReturn(conversationIDs);
        when(clientGateway.getPersonsByID(anyList(), eq(token))).thenReturn(Optional.of(expectedPersonModels));

        List<PersonModel> personModels = chatService.getChats(token);

        assertNotNull(personModels);
        assertEquals(expectedPersonModels, personModels);
        verify(clientGateway, times(1)).getPersonsByID(anyList(), eq(token));
    }

    @Test
    public void testGetMessages() {
        String token = "123456789";
        String idParceiro = "1";
        PersonModel person = mockPerson();
        ChatItem chatItem = new ChatItem();
        List<ChatItem> expectedChatItems = Arrays.asList(
                chatItem
        );
        when(clientGateway.getSessionId(token)).thenReturn(Optional.of(person));
        when(repository.findMessagesByConversationID(ChatItem.generateConversationID(idParceiro, person.getId()))).thenReturn(expectedChatItems);

        List<ChatItem> chatItems = chatService.getMessages(idParceiro, token);

        assertNotNull(chatItems);
        assertEquals(expectedChatItems, chatItems);
        verify(repository, times(1)).findMessagesByConversationID(ChatItem.generateConversationID(idParceiro, person.getId()));
    }

    private PersonModel mockPerson() {
        PersonModel person = new PersonModel();
        person.setId("2");
        return person;
    }

}