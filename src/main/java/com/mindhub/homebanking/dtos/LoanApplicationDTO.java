package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class LoanApplicationDTO {
    private long id;
    private Double amountInicial;
    private Double amountFinal;
    private String name;
    private int payments;
    private Double porcent;
    private String accountDestiny;

    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(ClientLoan clientloan, Loan loan) {
        this.id = clientloan.getId();
        this.amountInicial = clientloan.getAmountInicial();
        this.amountFinal = clientloan.getAmountFinal();
        this.name= loan.getName();
        this.payments = clientloan.getPayments();
        this.porcent = loan.getPorcent();
        this.accountDestiny = clientloan.getAccountNumber();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAccountDestiny() {
        return accountDestiny;
    }

    public void setAccountDestiny(String accountDestiny) {
        this.accountDestiny = accountDestiny;
    }


}
