package br.com.fiap.enginnering.chat.service;

import br.com.fiap.enginnering.chat.model.PersonModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "client-service", url = "http://localhost:8081/clientes/api/v1/")
public interface ClientGateway {
    @RequestMapping(method = RequestMethod.GET)
    Optional<PersonModel> getSessionId(@RequestHeader("Authorization") String token);

    @RequestMapping(method = RequestMethod.POST, path= "/users-by-ids")
    Optional<List<PersonModel>> getPersonsByID(List<String> ids, @RequestHeader("Authorization") String token);

}
