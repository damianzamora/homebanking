package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CardServiceImplement implements CardService{

    @Autowired
    private ClientRepository clientrepository;

    @Autowired
    private CardRepository cardRepository;


    @Override
    public ResponseEntity<Object> registerCards(CardType cardType, CardColor cardColor, Authentication authentication) {
        Client client= clientrepository.findByEmail(authentication.getName());
        if (client.getCards().stream().filter(e -> e.getCardType()==cardType).filter(e->e.isActive()==true).toArray().length < 3){
            cardRepository.save(new Card(cardType, cardColor, utils.getRandomNumber(1000,9999)+"-"+utils.getRandomNumber(1000,9999)+"-"+utils.getRandomNumber(1000,9999)+"-"+utils.getRandomNumber(1000,9999) ,
                    utils.getCVVrandom(), LocalDate.of(2021,06,01), LocalDate.of(2021,06,01).plusMonths(60),true, client));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else
        return new ResponseEntity<>("No puede crear mas de 3 tarjetas de tipo"+" "+cardType, HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<Object>EliminateCard(String Number , Authentication authentication ){

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
            return new ResponseEntity<>("Error en cliente autenticado", HttpStatus.FORBIDDEN);

    }
}
