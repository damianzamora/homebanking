package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private ClientRepository clientrepository;

    @Autowired
    private CardRepository cardRepository;

    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> registerCards(@RequestParam CardType cardType,
                                                @RequestParam CardColor cardColor,
                                                Authentication authentication) {


        Client client= clientrepository.findByEmail(authentication.getName());
        if (client.getCards().stream().filter(e -> e.getCardType()==cardType).filter(e->e.isActive()==true).toArray().length < 3){
            cardRepository.save(new Card(cardType, cardColor, utils.getRandomNumber(1000,9999)+"-"+utils.getRandomNumber(1000,9999)+"-"+utils.getRandomNumber(1000,9999)+"-"+utils.getRandomNumber(1000,9999) ,
                    utils.getCVVrandom(), LocalDate.of(2021,06,01), LocalDate.of(2021,06,01).plusMonths(60),true, client));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else
        { return new ResponseEntity<>("No puede crear mas de 3 tarjetas de tipo"+" "+cardType, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> EliminateCard(@RequestParam String Number , Authentication authentication
                                                ) {
        Client client= clientrepository.findByEmail(authentication.getName());
        if (client!=null){

            //Card card = cardRepository.findByNumber(Number);
            //cardRepository.delete(card);
            Card card =cardRepository.findByNumber(Number);
            card.setActive(false);
            cardRepository.save(card);
            return new ResponseEntity<>("Tarjeta inactivada/eliminada",HttpStatus.ACCEPTED);
        }
        else
        { return new ResponseEntity<>("Error en cliente autenticado", HttpStatus.FORBIDDEN);
        }




    }
}




