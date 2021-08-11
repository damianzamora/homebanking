package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ClientService {

    List<ClientDTO> getClients();

    ClientDTO getClient(Long id);

    ResponseEntity<Object> registerClient(String firstName,String lastName,String email,String password);

    ClientDTO getClientDTO(Authentication authentication);

    ResponseEntity<Object>cambioClave(String contraseñaActual,String contraseñaNueva,Authentication authentication);

    ResponseEntity<Object>cambioMail(String mailActual,String mailNuevo,Authentication authentication);


}
