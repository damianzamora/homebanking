package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.PagoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface PagoService {

    List<PagoDTO> getPagos();

    List<PagoDTO> getPagosFiltrados(String filtro, Authentication authentication);

    ResponseEntity<Object> crearPago(String cardNumber,Double amount,int cvv,
                                    String description,Authentication authentication);
}
