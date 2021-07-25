package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class ClientLoanDTO {

    private long id;
    private long loanId;
    private String name;
    private Double amountInicial;
    private Double amountFinal;
    private int payments;
    private Double porcent;
    private String AccountNumber;



    public ClientLoanDTO() {
    }

    public ClientLoanDTO(ClientLoan clientloan) {
        this.id=clientloan.getId();
        this.loanId=clientloan.getLoan().getId();
        this.name = clientloan.getLoan().getName();
        this.amountInicial = clientloan.getAmountInicial();
        this.amountFinal= clientloan.getAmountFinal();
        this.payments = clientloan.getPayments();
        this.porcent=clientloan.getLoan().getPorcent();
        this.AccountNumber = clientloan.getAccountNumber();

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public Double getPorcent() {
        return porcent;
    }

    public void setPorcent(Double porcent) {
        this.porcent = porcent;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }
}
