package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionsServiceImplement implements TransactionsService{

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository ;


    @Override
    public ResponseEntity<Object> registerTransaction(Double amount, String description, String numberOrigin, String numberDestiny, Authentication authentication) {

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
