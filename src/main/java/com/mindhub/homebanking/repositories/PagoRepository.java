package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.dtos.PagoDTO;
import com.mindhub.homebanking.models.Pago;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.awt.*;
import java.util.List;
import java.util.Optional;
@Repository
public interface PagoRepository extends JpaRepository <Pago, Long >,JpaSpecificationExecutor {
//Con SQL ACTIVADO
 //   @Query (value = "SELECT * FROM homebanking.pago WHERE description LIKE %?1% and client_id LIKE ?2 ",nativeQuery = true)
 //   List<Pago> findbydescription(@Param("filtro") String filtro,@Param("id_cliente") Long id_cliente );

    //CON H2 ACTIVADO
    @Query (value = "SELECT * FROM pago WHERE description LIKE %?1% and client_id LIKE ?2 ",nativeQuery = true)
    List<Pago> findbydescription(@Param("filtro") String filtro,@Param("id_cliente") Long id_cliente );
    //List<Pago> findByDescriptionContaining(@Param("filtro") String filtro);
    //

    }

