package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.services.TransactionsService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;


@RestController
@RequestMapping("/api")
public class TransactionsController {

   @Autowired
    TransactionsService transactionsService;

    @Transactional
    @PostMapping(path = "/clients/current/transactions")
    public ResponseEntity<Object> registerTransaction(@RequestParam Double amount,
                                                @RequestParam String description,
                                                @RequestParam String numberOrigin,
                                                @RequestParam String numberDestiny,
                                                Authentication authentication) {
        return transactionsService.registerTransaction(amount, description, numberOrigin, numberDestiny, authentication);

    }

}


