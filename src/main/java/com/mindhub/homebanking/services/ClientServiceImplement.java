package com.mindhub.homebanking.services;

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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService{
    @Autowired
    private ClientRepository clientrepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    LocalDateTime today = LocalDateTime.now();


    @Override
    public List<ClientDTO> getClients() {
        return clientrepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClient(Long id) {
        //return clientrepository.findById(id).map(ClientDTO::new).orElse(null); FORMA CORTA//
        Client client = this.clientrepository.findById(id).get();
        if (client != null ){
            ClientDTO clientDTO = new ClientDTO(client);
            return clientDTO;
        }
        return new ClientDTO();
    }


    @Override
    public ResponseEntity<Object> registerClient(String firstName, String lastName, String email, String password) {
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

    @Override
    public ClientDTO getClientDTO(Authentication authentication) {
        Client client= clientrepository.findByEmail(authentication.getName());
        ClientDTO clientDTO = new ClientDTO(client);
        return clientDTO;
    }


    @Override
    public ResponseEntity<Object> cambioMail(String mailActual, String mailNuevo, Authentication authentication) {

        if ( mailActual.isEmpty() || mailNuevo.isEmpty()){
            return new ResponseEntity<>("Error en los datos",HttpStatus.FORBIDDEN);
        }
        if (mailActual.equals(mailNuevo)){
            return new ResponseEntity<>("Mail actual no puede ser igual a mail nuevo",HttpStatus.FORBIDDEN);
        }

        if(clientrepository.findByEmail(mailNuevo)!=null){
            return new ResponseEntity<>("mail en uso, ingrese otro !",HttpStatus.FORBIDDEN);
        }

        Client client= clientrepository.findByEmail(authentication.getName());


        if (clientrepository.findByEmail(mailActual)!=null)
        {
            if(! (clientrepository.findByEmail(mailActual).getEmail() == client.getEmail())) {
                return new ResponseEntity<>("El mail no corresponde a tu usuario",HttpStatus.FORBIDDEN);
            }

            else {
                client.setEmail(mailNuevo);
                clientrepository.save(client);
                return new ResponseEntity<>("Mail actualizado", HttpStatus.ACCEPTED);
            }
        }
        else
            return new ResponseEntity<>("No se ha encontrado el email ingresado",HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<Object> cambioClave(String contraseñaActual, String contraseñaNueva, Authentication authentication) {
        if ( contraseñaActual.isEmpty() || contraseñaNueva.isEmpty()){
            return new ResponseEntity<>("Error en los datos",HttpStatus.FORBIDDEN);
        }
        if(contraseñaActual.equals(contraseñaNueva))
        {
            return new ResponseEntity<>("Ingrese una clave distinta a la actual",HttpStatus.FORBIDDEN);
        }
        Client client= clientrepository.findByEmail(authentication.getName());


        if (passwordEncoder.matches(contraseñaActual,client.getPassword() )) {
            //System.out.println("Coinciden las contraseñas");
            client.setPassword(passwordEncoder.encode(contraseñaNueva));
            clientrepository.save(client);
            return new ResponseEntity<>("Contraseña Actualizada", HttpStatus.ACCEPTED);
        }
        else
            return new ResponseEntity<>("Contraseña Incorrecta", HttpStatus.FORBIDDEN);
    }
}
