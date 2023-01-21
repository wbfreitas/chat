package br.com.fiap.enginnering.chat.repository;

import br.com.fiap.enginnering.chat.documents.ChatItem;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatRepository extends MongoRepository<ChatItem, String> {
    @Aggregation(pipeline = {
            "{'$match':{'conversationID': {$regex: ?0} }}}",
            "{$group: { '_id': '$conversationID' }}",
    })
    List<String> findConversationIDs(String personId);

    @Query("{conversationID: ?0}")
    List<ChatItem> findMessagesByConversationID(String generateConversationID);
}
