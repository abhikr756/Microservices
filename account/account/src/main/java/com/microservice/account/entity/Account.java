package com.microservice.account.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int act_id;
    private String accountNo;
    private double balance;
    private LocalDate createdAt;
    private LocalDate updatedAt;
     private int customerId;
    public int getAct_id() {
        return act_id;
    }

    public void setAct_id(int act_id) {
        this.act_id = act_id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public Account(int act_id, int customerId, String accountNo, double balance, LocalDate createdAt, LocalDate updatedAt) {
        this.act_id = act_id;
        this.customerId = customerId;
        this.accountNo = accountNo;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Account{" +
                "act_id=" + act_id +
                ", customerId=" + customerId +
                ", accountNo='" + accountNo + '\'' +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public Account() {
        super();
    }
}
