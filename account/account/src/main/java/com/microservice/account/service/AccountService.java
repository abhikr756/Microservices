package com.microservice.account.service;

import com.microservice.account.dto.AccountDetails;
import com.microservice.account.entity.Account;

public interface AccountService {

    Account createAccount(int customerId);

    Account addMoney(int accountId, double amount);

    Account withdrawMoney(int accountId, double amount);

    AccountDetails getAccountDetails(int accountId);

    void deleteAccount(int accountId);

    void deleteAccountByCustomerId(int customerId);
}
