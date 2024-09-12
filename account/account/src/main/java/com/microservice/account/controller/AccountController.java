package com.microservice.account.controller;

import com.microservice.account.dto.AccountDetails;
import com.microservice.account.entity.Account;
import com.microservice.account.exception.AccountNotFoundException;
import com.microservice.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;


@PostMapping("/create")
public ResponseEntity<Object> createAccount(@RequestParam int customerId) {
    try {
        Account createdAccount = accountService.createAccount(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    } catch (RuntimeException e) {
        String errorMessage = e.getMessage();
        if (errorMessage.contains("Account already exists for customer")) {
            // Account already exists for the customer
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account already exists for customer with ID: " + customerId);
        } else {
            // Customer not found
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account can't be created as customer with ID " + customerId + " not found");
        }
    }
}


    @PostMapping("/{accountId}/add-money")
    public ResponseEntity<Account> addMoney(@PathVariable int accountId, @RequestParam double amount) {
        try {
            System.out.println(accountId);
            System.out.println(amount);
            Account updatedAccount = accountService.addMoney(accountId, amount);
            return ResponseEntity.ok(updatedAccount);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{accountId}/withdraw-money")
    public ResponseEntity<Object> withdrawMoney(@PathVariable int accountId, @RequestParam double amount) {
        try {
            Account updatedAccount = accountService.withdrawMoney(accountId, amount);
            return ResponseEntity.ok(updatedAccount);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance in the account");
        }
    }


    @GetMapping("/{accountId}")
    public ResponseEntity<Object> getAccountDetails(@PathVariable int accountId) {
        try {
            AccountDetails accountDetails = accountService.getAccountDetails(accountId);
            return ResponseEntity.ok(accountDetails);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account does not exists with account id "+accountId);
        }
    }



    @DeleteMapping("/account-delete/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable int accountId) {
        try {
            accountService.deleteAccount(accountId);
            return ResponseEntity.ok("Account with ID " + accountId + " deleted successfully.");
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found with ID: " + accountId);
        }
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteAccountByCustomerId(@PathVariable int customerId) {
        try {
            accountService.deleteAccountByCustomerId(customerId);
            return ResponseEntity.ok("Account with ID " + customerId + " deleted successfully.");
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found with ID: " + customerId);
        }
    }

}
