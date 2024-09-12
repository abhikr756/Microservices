package com.microservice.customer.service;

import com.microservice.customer.Exception.CustomerNotFoundException;
import com.microservice.customer.dto.AccountDto;
import com.microservice.customer.entity.Customer;
import com.microservice.customer.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepo customerRepo;

    private final WebClient webClient;

    @Autowired
    public CustomerServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://ACCOUNT-SERVICE/accounts").build();
    }


    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    @Override
    public Customer getCustomerById(int customerId) {
        Optional<Customer> optionalCustomer = customerRepo.findById(customerId);
        return optionalCustomer.orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + customerId));
    }

    @Override
    public Customer updateCustomer(int customerId, Customer updatedCustomer) {
        Optional<Customer> optionalCustomer = customerRepo.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            existingCustomer.setAddress(updatedCustomer.getAddress());
            // Update other fields as needed
            return customerRepo.save(existingCustomer);
        } else {
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }
    }

    @Override
    public void deleteCustomer(int customerId) {
        Optional<Customer> optionalCustomer = customerRepo.findById(customerId);
        if (optionalCustomer.isPresent()) {
            String accountMono = webClient.delete()
                    .uri("/{customerId}", customerId)
                    .retrieve()
                    .bodyToMono(String.class).block();
            customerRepo.deleteById(customerId);
        } else {
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }
    }

}
