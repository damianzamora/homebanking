package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

public interface CardService {

     ResponseEntity<Object> registerCards ( CardType cardType,CardColor cardColor,Authentication authentication);

     ResponseEntity<Object> EliminateCard(String Number , Authentication authentication );



}
