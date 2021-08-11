package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.List;


@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    LoanService loanService;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanService.getLoans();
    }

    @Transactional
    @PostMapping(path = "/loans")
    public ResponseEntity<?>CreateNewLoans(@RequestParam String nombrePrestamo,
                                                @RequestParam Double amountMax,
                                                @RequestParam Double porcent,
                                                @RequestParam List<Integer> payments
                                                ){
    return loanService.CreateNewLoans(nombrePrestamo, amountMax, porcent, payments);
    }

    @Transactional
    @PostMapping(path = "/clients/current/loans")
    public ResponseEntity<String> registerLoans(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {

    return loanService.registerLoans(loanApplicationDTO,authentication);
    }

}
