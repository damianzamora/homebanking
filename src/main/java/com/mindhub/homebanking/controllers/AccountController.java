package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientrepository;

    LocalDateTime today = LocalDateTime.now();

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        //return clientrepository.findById(id).map(ClientDTO::new).orElse(null); FORMA CORTA//
        Account account = this.accountRepository.findById(id).get();
        if (account != null) {
            AccountDTO accountDTO = new AccountDTO(account);
            return accountDTO;
        }
        return new AccountDTO();
    }

    @PostMapping(path = "/clients/current/accounts")

    public ResponseEntity<Object> CrearCuenta(@RequestParam String tipoCuenta,Authentication authentication) {
        Client client = clientrepository.findByEmail(authentication.getName());
        if (client.getAccounts().stream().filter(e->e.isActive()==true).toArray().length < 3) {
            accountRepository.save(new Account("VIN" + utils.getRandomNumber(1, 999999999), today,tipoCuenta, 0.0, true, client));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("No puedes crear mas de 3 cuentas", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(path = "/clients/current/Deleteaccounts")
    public ResponseEntity<Object> EliminarCuenta(@RequestParam String number, Authentication authentication) {
        Account account = accountRepository.findByNumber(number);
        account.setActive(false);

        for(Transaction transaccion  : account.getTransactions()){
            transaccion.setActive(false);

        }
        accountRepository.save(account);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

}