package br.com.fiap.enginnering.chat.repository;

import br.com.fiap.enginnering.chat.documents.ChatItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatRepository extends MongoRepository<ChatItem, String> {

    @Query("{conversationID: '?0'}")
    List<ChatItem> findByConversationID(String conversationID);
}
