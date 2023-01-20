package br.com.fiap.enginnering.chat.documents;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Document("chat")
public class ChatItem {

   @Id
    private String id;

    @NotNull(message = "Campo message obrigatorio")
    private String message;

    @Field(name = "talker_id")
    @JsonAlias(value = "talker_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String talkerID;

    @Field(name = "receiver_id")
    @JsonAlias(value = "receiver_id")
    @NotNull(message = "Campo receiver_id obrigatorio")
    private String receiverID;

    private String conversationID;
    private Date create;

    public ChatItem build() {
        this.conversationID = this.generateConversationID(this.talkerID, this.receiverID);
        this.create = new Date();
        return this;
    }

    public static String generateConversationID(String... id) {
        return Arrays.stream(id)
                .sorted()
                .collect(Collectors.joining("/"));
    }

    public Date getCreate() {
        return create;
    }

    public String getConversationID() {
        return conversationID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTalkerID() {
        return talkerID;
    }

    public void setTalkerID(String talkerID) {
        this.talkerID = talkerID;
    }
}
