package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AccountService {

    List<AccountDTO>getAccounts();

    AccountDTO getAccount(Long id);

    ResponseEntity<Object>CrearCuenta(String tipoCuenta, Authentication authentication);

    ResponseEntity<Object>EliminarCuenta(String number);
}
