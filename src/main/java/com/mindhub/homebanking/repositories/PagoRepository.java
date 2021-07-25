package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Pago;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long > {

}
