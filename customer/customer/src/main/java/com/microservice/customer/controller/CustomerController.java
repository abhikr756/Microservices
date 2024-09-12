package com.microservice.customer.controller;

import com.microservice.customer.Exception.CustomerNotFoundException;
import com.microservice.customer.dto.AccountDto;
import com.microservice.customer.entity.Customer;
import com.microservice.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Object> getCustomerById(@PathVariable int customerId) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            return ResponseEntity.ok(customer);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found with ID: " + customerId);
        }
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Object> updateCustomer(@PathVariable int customerId, @RequestBody Customer customer) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(customerId, customer);
            return ResponseEntity.ok(updatedCustomer);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found with ID: " + customerId);
        }
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int customerId) {
        try {
            customerService.deleteCustomer(customerId);
            return ResponseEntity.ok("Customer and it's account with ID " + customerId + " deleted successfully.");
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found with ID: " + customerId);
        }
    }

}
