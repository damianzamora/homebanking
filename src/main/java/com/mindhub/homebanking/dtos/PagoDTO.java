package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Pago;

import java.time.LocalDateTime;

public class PagoDTO {
    private long id;
    private LocalDateTime creationDate;
    private Double amount;
    private String cardNumber;
    private String accountNumber;
    private String description;

    public PagoDTO() {
    }

    public PagoDTO(Pago pago) {
        this.creationDate = pago.getCreationDate();
        this.amount = pago.getAmount();
        this.cardNumber = pago.getCardNumber();
        this.accountNumber = pago.getAccountNumber();
        this.description = pago.getDescription();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
