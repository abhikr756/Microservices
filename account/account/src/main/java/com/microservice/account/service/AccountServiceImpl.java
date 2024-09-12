package com.microservice.account.service;

import com.microservice.account.dto.AccountDetails;
import com.microservice.account.dto.CustomerDto;
import com.microservice.account.entity.Account;
import com.microservice.account.exception.AccountNotFoundException;
import com.microservice.account.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl  implements AccountService{


    @Autowired
    private AccountRepo accountRepo;

    private final WebClient webClient;

    @Autowired
    public AccountServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://CUSTOMER-SERVICE/customers").build();
    }


@Override
public Account createAccount(int customerId) {
    // Check if the customer exists before creating the account
    Mono<CustomerDto> customerMono = webClient.get()
            .uri("/{customerId}", customerId)
            .retrieve()
            .bodyToMono(CustomerDto.class);

    CustomerDto customerDto = customerMono.block(); // Blocking to get the result synchronously

    if (customerDto != null) {
        // Check if an account already exists for the customer
        Account existingAccount = accountRepo.findByCustomerId(customerId);
        if (existingAccount != null) {
            throw new RuntimeException("Account already exists for customer with ID: " + customerId);
        }

        // Customer exists, proceed with creating the account
        Account newAccount = new Account();
        newAccount.setAccountNo(generateAccountNumber());
        newAccount.setBalance(0.0); // Initial balance
        newAccount.setCreatedAt(LocalDate.now());
        newAccount.setUpdatedAt(LocalDate.now());
        newAccount.setCustomerId(customerDto.getCustomerId());
        return accountRepo.save(newAccount);
    } else {
        // Customer does not exist, throw an exception or handle accordingly
        throw new RuntimeException("Customer not found with ID: " + customerId);
    }
}



    @Override
    public Account addMoney(int accountId, double amount) {
        Optional<Account> optionalAccount = accountRepo.findById(accountId);

        if (optionalAccount.isPresent()) {
            // Account exists, add money to the existing account
            Account existingAccount = optionalAccount.get();
            existingAccount.setBalance(existingAccount.getBalance() + amount);
            existingAccount.setUpdatedAt(LocalDate.now());
            return accountRepo.save(existingAccount);
        } else {
            // Account does not exist, create a new account
            Account newAccount = new Account();
            newAccount.setAccountNo(generateAccountNumber()); // Implement the logic to generate a new account number
            newAccount.setBalance(amount);
            newAccount.setCreatedAt(LocalDate.now());
            newAccount.setUpdatedAt(LocalDate.now());
            return accountRepo.save(newAccount);
        }
    }




    @Override
    public Account withdrawMoney(int accountId, double amount) {
        Account account = getAccountById(accountId);
        double currentBalance = account.getBalance();
        if (currentBalance < amount) {
            // Handle insufficient balance scenario, you may throw a custom exception
            throw new RuntimeException("Insufficient balance in the account");
        }
        account.setBalance(currentBalance - amount);
        return accountRepo.save(account);
    }

    @Override
    public AccountDetails getAccountDetails(int accountId) {
        Optional<Account> optionalAccount = accountRepo.findById(accountId);
        if(optionalAccount.isPresent()) {
            CustomerDto customerMono = webClient.get()
                    .uri("/{customerId}", optionalAccount.get().getCustomerId())
                    .retrieve()
                    .bodyToMono(CustomerDto.class).block();
            AccountDetails accountDetails=new AccountDetails();
            accountDetails.setAccountNo(optionalAccount.get().getAccountNo());
            accountDetails.setBalance(optionalAccount.get().getBalance());
            accountDetails.setAct_id(optionalAccount.get().getAct_id());
            accountDetails.setCustomerId(optionalAccount.get().getCustomerId());
            accountDetails.setName(customerMono.getName());
            accountDetails.setAddress(customerMono.getAddress());
            accountDetails.setEmail(customerMono.getEmail());
            accountDetails.setPhoneNumber(customerMono.getPhoneNumber());
            return accountDetails;
        }
        else{
            throw new AccountNotFoundException("Account not found with ID: " + accountId);
        }
    }
    @Override
    public void deleteAccount(int accountId) {
        accountRepo.deleteById(accountId);
    }

    @Override
    public void deleteAccountByCustomerId(int customerId) {
        Account accountExists =accountRepo.findByCustomerId(customerId);
        if(accountExists!=null){
            accountRepo.delete(accountExists);
        }

    }


    private Account getAccountById(int accountId) {
        Account optionalAccount = accountRepo.findById(accountId).get();
        return optionalAccount;
    }


    private String generateAccountNumber() {
        Random random = new Random();

        // Generate a random 14-digit account number
        StringBuilder accountNumberBuilder = new StringBuilder();
        for (int i = 0; i < 14; i++) {
            accountNumberBuilder.append(random.nextInt(10));
        }

        return accountNumberBuilder.toString();
    }



    public Mono<CustomerDto> getCustomerByAccountId(Long accountId) {
        return webClient.get()
                .uri("/customers/{customerId}", accountId)
                .retrieve()
                .bodyToMono(CustomerDto.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    if (e.getStatusCode().is4xxClientError()) {
                        // Handle 4xx errors (e.g., customer not found)
                        return Mono.empty();
                    } else {
                        // Propagate other errors
                        return Mono.error(e);
                    }
                });
    }

}
