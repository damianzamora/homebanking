package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;


import java.util.List;


public interface LoanService {

    List<LoanDTO> getLoans();

    ResponseEntity<String> CreateNewLoans(String nombrePrestamo,Double amountMax,Double porcent,
                                        List<Integer> payments );


    ResponseEntity<String> registerLoans (LoanApplicationDTO loanApplicationDTO, Authentication authentication );


}
