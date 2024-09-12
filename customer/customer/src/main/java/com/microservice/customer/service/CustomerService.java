package com.microservice.customer.service;

import com.microservice.customer.dto.AccountDto;
import com.microservice.customer.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer addCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerById(int customerId);

    Customer updateCustomer(int customerId, Customer customer);

    void deleteCustomer(int customerId);
}
