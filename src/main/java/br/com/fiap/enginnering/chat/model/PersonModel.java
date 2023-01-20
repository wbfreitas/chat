package br.com.fiap.enginnering.chat.model;

import lombok.Data;
import org.bson.types.Binary;

@Data
public class PersonModel {
    private String id;
    private String nome;
    private String sobrenome;
    private String email;
    private boolean mamae = false;
    private Binary avatar;
}
