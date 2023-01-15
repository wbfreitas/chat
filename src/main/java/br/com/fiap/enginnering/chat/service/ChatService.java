package br.com.fiap.enginnering.chat.service;

import br.com.fiap.enginnering.chat.documents.ChatItem;
import br.com.fiap.enginnering.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    ChatRepository repository;
    public ChatItem save(ChatItem item) {
        return repository.save(item.build());
    }
    public List<ChatItem> conversation(String... id) {
        return repository.findByConversationID(ChatItem.generateConversationID(id));
    }

}
