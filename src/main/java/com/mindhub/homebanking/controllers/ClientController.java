package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientrepository;

    @Autowired
    private AccountRepository accountRepository;

    LocalDateTime today = LocalDateTime.now();

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientrepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

@GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        //return clientrepository.findById(id).map(ClientDTO::new).orElse(null); FORMA CORTA//
        Client client = this.clientrepository.findById(id).get();
        if (client != null ){
            ClientDTO clientDTO = new ClientDTO(client);
            return clientDTO;
        }
        return new ClientDTO();
        }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping(path = "/clients")

    public ResponseEntity<Object> register(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientrepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        Client nuevoCliente = clientrepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));
        accountRepository.save(new Account("VIN"+ utils.getRandomNumber(1,999999999),today,"Corriente", 0.0,true,nuevoCliente));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/clients/current")
    public ClientDTO getAll(Authentication authentication) {
        Client client= clientrepository.findByEmail(authentication.getName());
        ClientDTO clientDTO = new ClientDTO(client);
        return clientDTO;

    }

}