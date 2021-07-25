package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Double amountInicial;
    private Double amountFinal;
    private Integer payments;
    private String accountNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="loan")
    private Loan loan;



    public ClientLoan() {
    }

    public ClientLoan(Double amountInicial,Double amountFinal, Integer payments, Client client, Loan loan,String accountNumber) {
        this.amountInicial = amountInicial;
        this.amountFinal = amountFinal;
        this.payments = payments;
        this.client = client;
        this.loan = loan;
        this.accountNumber=accountNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getAmountInicial() {
        return amountInicial;
    }

    public void setAmountInicial(Double amountInicial) {
        this.amountInicial = amountInicial;
    }

    public Double getAmountFinal() {
        return amountFinal;
    }

    public void setAmountFinal(Double amountFinal) {
        this.amountFinal = amountFinal;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
