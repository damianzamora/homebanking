package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class ClientController {

@Autowired
    ClientService clientService;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientService.getClients();
    }

@GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientService.getClient(id);
}


    @PostMapping(path = "/clients")
    public ResponseEntity<Object> registerClient(@RequestParam String firstName,@RequestParam String lastName,
                                                 @RequestParam String email,@RequestParam String password) {
        return clientService.registerClient(firstName, lastName, email, password);

    }

    @GetMapping("/clients/current")
    public ClientDTO getClientDTO(Authentication authentication) {
        return clientService.getClientDTO(authentication);
    }

    @Transactional
    @PostMapping(path = "/clients/current/cambioMail")
    public ResponseEntity<Object> cambioMail(@RequestParam String mailActual,@RequestParam String mailNuevo,
                                               Authentication authentication){
        return clientService.cambioMail(mailActual, mailNuevo, authentication);
    }

    @Transactional
    @PostMapping(path = "/clients/current/cambioContraseña")
    public ResponseEntity<Object> cambioClave(@RequestParam String contraseñaActual,
                                               @RequestParam String contraseñaNueva,
                                               Authentication authentication){

        return clientService.cambioClave(contraseñaActual, contraseñaNueva, authentication);

    }


}