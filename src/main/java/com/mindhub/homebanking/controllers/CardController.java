package com.mindhub.homebanking.controllers;



import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    CardService cardService;

    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> registerCards(@RequestParam CardType cardType,
                                                @RequestParam CardColor cardColor,
                                                Authentication authentication) {
        return cardService.registerCards(cardType,cardColor,authentication);
    }

    @PutMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> EliminateCard(@RequestParam String Number , Authentication authentication
                                                ) {
        return cardService.EliminateCard(Number,authentication);
    }
}




