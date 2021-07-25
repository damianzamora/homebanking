package com.mindhub.homebanking;


import com.mindhub.homebanking.utils.utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;



import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest
public class Util {

    @Test
    public void cardNumberIsCreated() {
        Integer cvVrandom = utils.getCVVrandom();
        assertThat(cvVrandom, is(not(0)));
    }



}
