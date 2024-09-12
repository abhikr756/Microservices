package com.microservice.customer.dto;

import java.time.LocalDate;

public class AccountDto {
    private int act_id;
    private String accountNo;
    private double balance;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private int customerId;

    public AccountDto(int act_id, String accountNo, double balance, LocalDate createdAt, LocalDate updatedAt, int customerId) {
        this.act_id = act_id;
        this.accountNo = accountNo;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.customerId = customerId;

    }

    public int getAct_id() {
        return act_id;
    }

    public void setAct_id(int act_id) {
        this.act_id = act_id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "act_id=" + act_id +
                ", accountNo='" + accountNo + '\'' +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", customerId=" + customerId +
                '}';
    }
}