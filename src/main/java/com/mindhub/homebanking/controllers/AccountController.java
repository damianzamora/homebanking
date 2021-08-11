package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountService.getAccounts();
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    @PostMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> CrearCuenta(@RequestParam String tipoCuenta,Authentication authentication) {
        return accountService.CrearCuenta(tipoCuenta, authentication);
    }

    @PostMapping(path = "/clients/current/Deleteaccounts")
    public ResponseEntity<Object> EliminarCuenta(@RequestParam String number) {
        return accountService.EliminarCuenta(number);
    }

}