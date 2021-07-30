package com.mindhub.homebanking.utils;


import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.Pago;
import com.mindhub.homebanking.repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;

public class utils {

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static int getCVVrandom(){
        String aux = String.valueOf(getRandomNumber(1,9))+String.valueOf(getRandomNumber(1,9))+String.valueOf(getRandomNumber(1,9));
        int cvv = Integer.parseInt(aux);
        return cvv;
    }
    public static boolean cuotasCorrespondeAlPrestamo(int cuotas , Loan loan){

        if ( loan.getPayments().contains(cuotas)){
            return true;
        }
        else
        {
            return false;
        }
    }



}

