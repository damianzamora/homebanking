package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.security.core.Authentication;

import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionsController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;



    @Autowired
    private ClientRepository   clientRepository ;

    @Transactional
    @PostMapping(path = "/clients/current/transactions")
    public ResponseEntity<Object> registerCards(@RequestParam Double amount,
                                                @RequestParam String description,
                                                @RequestParam String numberOrigin,
                                                @RequestParam String numberDestiny,
                                                Authentication authentication) {

        //Client client= clientrepository.findByEmail(authentication.getName());
    if(amount <= 0 || description.isEmpty() || numberOrigin.isEmpty() || numberDestiny.isEmpty()){
        return new ResponseEntity<>("Detectamos problemas con los datos ingresados", HttpStatus.FORBIDDEN);
    }
    if (accountRepository.findByNumber(numberOrigin)==null){
        return new ResponseEntity<>("Cuenta de origen inexistente", HttpStatus.FORBIDDEN);
    }
    if (accountRepository.findByNumber(numberDestiny)==null){
        return new ResponseEntity<>("Cuenta de destino inexistente", HttpStatus.FORBIDDEN);
    }
    if(numberOrigin.equals(numberDestiny) ){
        return new ResponseEntity<>("No se puede transferir a la misma cuenta", HttpStatus.FORBIDDEN);
    }
    if(accountRepository.findByNumber(numberOrigin).getBalance() < amount){
        return new ResponseEntity<>("No hay fondos disponibles", HttpStatus.FORBIDDEN);
    }
    else{
        double balanceActualOrigin= (accountRepository.findByNumber(numberOrigin).getBalance())-amount;
        double balanceActualDestiny= (accountRepository.findByNumber(numberDestiny).getBalance())+amount;

        transactionRepository.save(new Transaction(TransactionType.DEBITO,amount,description, LocalDateTime.now(),balanceActualOrigin,accountRepository.findByNumber(numberOrigin),true));
        transactionRepository.save(new Transaction(TransactionType.CREDITO,amount,description, LocalDateTime.now(),balanceActualDestiny,accountRepository.findByNumber(numberDestiny),true));


        accountRepository.findByNumber(numberOrigin).setBalance(balanceActualOrigin);
        accountRepository.save(accountRepository.findByNumber(numberOrigin));

        accountRepository.findByNumber(numberDestiny).setBalance(balanceActualDestiny);
        accountRepository.save(accountRepository.findByNumber(numberDestiny));
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    }




}


