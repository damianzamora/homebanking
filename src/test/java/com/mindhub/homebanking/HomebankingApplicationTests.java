package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest
class HomebankingApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	LoanRepository loanRepository;
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	CardRepository cardRepository;
	@Test

	public void existLoans(){
		List<Loan> loans = loanRepository.findAll();
		assertThat(loans,is(not(empty())));

	}

	@Test
	public void existClient(){
		List<Client> clients = clientRepository.findAll();
		assertThat(clients,is(not(empty())));
	}

	@Test
	public void existCardColorGold(){
		List<Card> cards = cardRepository.findAll();
		assertThat(cards, hasItem(hasProperty("cardColor", is(CardColor.GOLD))));
	}


	@Test
	public void existPersonalLoan(){
		List<Loan> loans = loanRepository.findAll();
		assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
	}




}
