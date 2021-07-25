package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping(path = "/loans")
    public ResponseEntity<?>CreateNewLoans(
                                                @RequestParam String nombrePrestamo,
                                                @RequestParam Double amountMax,
                                                @RequestParam Double porcent,
                                                @RequestParam List<Integer> payments
                                                ){
        if ( nombrePrestamo==null || amountMax==0 || porcent<0 ){
            return new ResponseEntity<>("Detectamos problemas con los datos ingresados", HttpStatus.FORBIDDEN);
        }

        if (loanRepository.findByName(nombrePrestamo) !=  null){
            return new ResponseEntity<>("Préstamo ya existente", HttpStatus.FORBIDDEN);
        }
        if(payments.isEmpty()){
            return new ResponseEntity<>("No ingreso las cuotas", HttpStatus.FORBIDDEN);
        }
            loanRepository.save(new Loan(nombrePrestamo, amountMax, porcent, payments));
            return new ResponseEntity<>("Préstamo creado.", HttpStatus.ACCEPTED);

    }

    @Transactional
    @PostMapping(path = "/clients/current/loans")
    public ResponseEntity<String> registerLoans(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {


        if (loanApplicationDTO.getAmountInicial() == 0 || loanApplicationDTO.getPayments() == 0 || loanApplicationDTO.getAccountDestiny().isEmpty() ) {
            return new ResponseEntity<>("Detectamos problemas con los datos ingresados", HttpStatus.FORBIDDEN);
        }

        if (accountRepository.findByNumber(loanApplicationDTO.getAccountDestiny()) == null) {
            return new ResponseEntity<>("Cuenta de destino inexistente", HttpStatus.FORBIDDEN);
        }
        if(loanApplicationDTO.getAmountInicial() > loanRepository.findByName(loanApplicationDTO.getName()).getMaxAmount())
        {
            return new ResponseEntity<>("El monto solicitado excede el monto máximo permitido",HttpStatus.FORBIDDEN);
        }
        if (! utils.cuotasCorrespondeAlPrestamo(loanApplicationDTO.getPayments(),loanRepository.findByName(loanApplicationDTO.getName()))) {
            return new ResponseEntity<>("Las cuotas seleccionadas no corresponden al tipo de prestamo solicitado", HttpStatus.FORBIDDEN);
        }
        else {

            double balanceActualDestiny = (accountRepository.findByNumber(loanApplicationDTO.getAccountDestiny()).getBalance()) + loanApplicationDTO.getAmountInicial();

            transactionRepository.save(new Transaction(TransactionType.CREDITO, loanApplicationDTO.getAmountInicial(), "loan approved" + " " + loanApplicationDTO.getName(), LocalDateTime.now(), balanceActualDestiny, accountRepository.findByNumber(loanApplicationDTO.getAccountDestiny()), true));
            System.out.println(loanApplicationDTO.getPorcent());
            accountRepository.findByNumber(loanApplicationDTO.getAccountDestiny()).setBalance(balanceActualDestiny);
            clientLoanRepository.save(new ClientLoan(loanApplicationDTO.getAmountInicial(), ((loanApplicationDTO.getAmountInicial() * loanApplicationDTO.getPorcent()) / 100) + loanApplicationDTO.getAmountInicial(), loanApplicationDTO.getPayments(), clientRepository.findByEmail(authentication.getName()), loanRepository.findByName(loanApplicationDTO.getName()), loanApplicationDTO.getAccountDestiny()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }


    }

}
