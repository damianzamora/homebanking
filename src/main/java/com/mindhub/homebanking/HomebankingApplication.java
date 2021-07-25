package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.plaf.synth.ColorType;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEnconder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);



	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountrepository
			, TransactionRepository transactionrepository , LoanRepository loanrepository ,
			ClientLoanRepository clientloanrepository, CardRepository cardrepository,
									  PagoRepository pagoRepository) {
		return (args) -> {

			Client client1 = clientRepository.save(new Client("Melba", "Morel","melba@mindhub.com",passwordEnconder.encode("qwert")));
			Client client2 = clientRepository.save(new Client("Damilba", "Morelinho","lodsaa@mindhub.com",passwordEnconder.encode("hola123456")));
			Client admin = clientRepository.save(new Client("admin","admin","admin@mindhub.com", passwordEnconder.encode("admin123")));
			LocalDateTime today = LocalDateTime.now();
			LocalDateTime tomorrow = today.plusDays(1);
			Account account1 =accountrepository.save(new Account("VIN001",today,"Ahorro", 5000.0,true,client1));
			Account account2 =accountrepository.save(new Account("VIN002",tomorrow,"Corriente", 7500.0,true,client1));
			Account account3 =accountrepository.save(new Account("VIN003",tomorrow,"Ahorro", 17500.0,true,client2));
			Account account4 =accountrepository.save(new Account("VINADMIN",tomorrow,"Ahorro", 9999.0,true,admin));
			System.out.println("- - - - - - - - - - - - - - - - - - - - - - -");
			//System.out.println(client1.getFirstName()+""+client1.getLastName());
			//System.out.println(repository.findAll().get(1).getAccounts().toString());
			//Transaction transaction1 =transactionrepository.save(new Transaction(TransactionType.DEBITO,500.0,"Cuota",LocalDateTime.now(),account1));
			//Transaction transaction2 =transactionrepository.save(new Transaction(TransactionType.CREDITO,+8000.0,"Aguinaldo",LocalDateTime.now(),account1));
			//Transaction transaction3 =transactionrepository.save(new Transaction(TransactionType.CREDITO,+16000.0,"Sueldo",LocalDateTime.now(),account1));
			//Transaction transaction4 =transactionrepository.save(new Transaction(TransactionType.DEBITO,6000.0,"Televisor",LocalDateTime.now(),account1));
			//Transaction transaction5 =transactionrepository.save(new Transaction(TransactionType.CREDITO,+56000.0,"Sueldo",today,account2));
			//Transaction transaction6 =transactionrepository.save(new Transaction(TransactionType.DEBITO,2000.0,"PedidosYa",today,account2));
			Loan loan1 = loanrepository.save(new Loan("Hipotecario" , 500000.,15.00, List.of(12,24,36,48,60)));
			Loan loan2 = loanrepository.save(new Loan("Personal" , 100000.,30.00, List.of(6,12,24)));
			Loan loan3 = loanrepository.save(new Loan("Automotriz" , 300000.,22.00, List.of(6,12,24,36)));
			//Pago pago1 = pagoRepository.save(new Pago(today,2000.0,"4444-5555-6666-7777","VIN001","ML",client1));
			//ClientLoan clienteloan1 = clientloanrepository.save(new ClientLoan(400000.	, 60 , client1,loan1));
			//ClientLoan clienteloan2 = clientloanrepository.save(new ClientLoan(50000.	, 12 , client1,loan2));
			//ClientLoan clienteloan3 = clientloanrepository.save(new ClientLoan(100000.	, 24 , client2,loan1));
			//ClientLoan clienteloan4 = clientloanrepository.save(new ClientLoan(200000.	, 36 , client2,loan3));
			Card card1 = cardrepository.save(new Card(CardType.DEBIT, CardColor.GOLD, "4546 7825 7496 2541", 234, LocalDate.of(2021,06,01), LocalDate.of(2021,04,01),true, client1));
			Card card2 = cardrepository.save(new Card(CardType.CREDIT,CardColor.TITANIUM,"4865 7825 7496 5879",235,LocalDate.of(2021,06,01),LocalDate.of(2021,06,01).plusMonths(60),true, client1));
			Card card3 = cardrepository.save(new Card(CardType.CREDIT,CardColor.GOLD,"5569 5825 3496 2879",225,LocalDate.of(2021,06,01),LocalDate.of(2021,06,01).plusMonths(60),true, client1));
			Card card4 = cardrepository.save(new Card(CardType.DEBIT,CardColor.SILVER,"3698 5825 3496 2879",205,LocalDate.of(2021,06,01),LocalDate.of(2021,06,01).plusMonths(60),true, client1));
			Card card5 = cardrepository.save(new Card(CardType.CREDIT, CardColor.SILVER,"5897 7825 7496 2368",236,LocalDate.of(2021,06,01), LocalDate.of(2021,06,01).plusMonths(60),true,client2 ));
			System.out.println("- - - - - - - - - - - - - - - Completado- - - - - - - - - - - - - - - - - - - - - - -");

		};
	}


}
