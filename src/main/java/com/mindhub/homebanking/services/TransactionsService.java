package com.mindhub.homebanking.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

public interface TransactionsService {

    ResponseEntity<Object>registerTransaction(Double amount,String description,String numberOrigin,
                                              String numberDestiny,Authentication authentication);
}
