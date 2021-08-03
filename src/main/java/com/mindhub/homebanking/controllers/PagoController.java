package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.PagoDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import net.bytebuddy.asm.Advice;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PagoController {
    @Autowired
    PagoRepository pagoRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository ;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;



    @GetMapping("/pagos")
    public List<PagoDTO> getPagos() {
        return pagoRepository.findAll().stream().map(pago -> new PagoDTO(pago)).collect(Collectors.toList());
    }

    @GetMapping(path= "/pagoFiltrar")
    public List<PagoDTO> getPagosFiltrados(@RequestParam String filtro, Authentication authentication){
        /*
        List<Pago> pagos = pagoRepository.findbydescription(filtro);
        for(Pago s : pagos){
            System.out.println("Pago filtrado: "+s.getAmount()+" "+s.getDescription()+" "+s.getAccountNumber() );
        }
        */
        Client client= clientRepository.findByEmail(authentication.getName());
        return pagoRepository.findbydescription(filtro.toUpperCase(), client.getId()).stream().map(pago -> new PagoDTO(pago)).collect(Collectors.toList());

    }

    @Transactional
    @PostMapping(path = "/clients/current/pago")
    public ResponseEntity<Object> crearPago     (@RequestParam String cardNumber,
                                                 @RequestParam Double amount,
                                                 @RequestParam int cvv,
                                                 @RequestParam String description,

                                                 Authentication authentication) {

        if(amount<=0 || description.isEmpty()){
            return new ResponseEntity<>("Revise el monto o descripcion ingresado", HttpStatus.FORBIDDEN);
        }
        if (cardRepository.findByNumber(cardNumber)==null) {
            return new ResponseEntity<>("Tarjeta inexistente", HttpStatus.FORBIDDEN);
        }

        Card card= cardRepository.findByNumber(cardNumber);
        if(card.getThruDate().isBefore(LocalDate.now())){
            return new ResponseEntity<>("La tarjeta esta vencida,pruebe con otra",HttpStatus.FORBIDDEN);
        }

        if(card.getCvv()!=cvv){
            return new ResponseEntity<>("CVV incorrecto", HttpStatus.FORBIDDEN);
        }

        Client client= clientRepository.findByEmail(authentication.getName());
        Set<AccountDTO> accounts  = client.getAccounts().stream().filter(a -> a.isActive()).map(AccountDTO::new).collect(Collectors.toSet());

        AccountDTO account = null;
        for (AccountDTO temp : accounts) {
            if(temp.getBalance()>=amount)
            {
                account=temp;
                //System.out.println(account.getNumber());
            }
        }
        if (account == null) {
            return new ResponseEntity<>("No tienen fondos disponibles o no tienes una cuenta activa", HttpStatus.FORBIDDEN);
        }
        double balanceActualOrigin= (account.getBalance()-amount);
        transactionRepository.save(new Transaction(TransactionType.DEBITO,amount,cardNumber+" "+description, LocalDateTime.now(),balanceActualOrigin,accountRepository.findByNumber(account.getNumber()),true));
        accountRepository.findByNumber(account.getNumber()).setBalance(balanceActualOrigin);
        accountRepository.save(accountRepository.findByNumber(account.getNumber()));
        pagoRepository.save(new Pago(LocalDateTime.now(),amount,cardNumber, account.getNumber(), description,client));
        return new ResponseEntity<>("Se aplicara en cuenta:"+account.getNumber(),HttpStatus.CREATED);
    }


}
