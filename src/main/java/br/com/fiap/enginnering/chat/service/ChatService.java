package br.com.fiap.enginnering.chat.service;

import br.com.fiap.enginnering.chat.documents.ChatItem;
import br.com.fiap.enginnering.chat.model.PersonModel;
import br.com.fiap.enginnering.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatService {
    @Autowired
    ChatRepository repository;

    @Autowired
    ClientGateway clientGateway;
    public ChatItem save(ChatItem item, String token) {
        PersonModel person = clientGateway.getSessionId(token).get();
        item.setTalkerID(person.getId());
        return repository.save(item.build());
    }
    public List<PersonModel> getChats(String token) {
        PersonModel person = clientGateway.getSessionId(token).get();
        List<String> personID = repository
                .findConversationIDs(person.getId())
                .stream().map(c -> c.replace("/", "")
                        .replace(person.getId(), "")).collect(Collectors.toList());

        Optional<List<PersonModel>> personsByID = clientGateway.getPersonsByID(personID, token);
        return  personsByID.get();
    }

    public List<ChatItem> getMessages(String idParceiro, String token) {
        PersonModel person = clientGateway.getSessionId(token).get();
       return repository.findMessagesByConversationID(ChatItem.generateConversationID(idParceiro, person.getId()));
    }
}
