package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.PagoDTO;
import com.mindhub.homebanking.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    PagoService pagoService;


    @GetMapping("/pagos")
    public List<PagoDTO> getPagos() {
        return pagoService.getPagos();
    }

    @GetMapping(path = "/pagoFiltrar")
    public List<PagoDTO> getPagosFiltrados(@RequestParam String filtro, Authentication authentication) {
        return pagoService.getPagosFiltrados(filtro, authentication);
    }

    @Transactional
    @PostMapping(path = "/clients/current/pago")
    public ResponseEntity<Object> crearPago(@RequestParam String cardNumber,
                                            @RequestParam Double amount,
                                            @RequestParam int cvv,
                                            @RequestParam String description,
                                            Authentication authentication) {
        return pagoService.crearPago(cardNumber, amount, cvv, description, authentication);
    }

}
