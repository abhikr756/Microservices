package com.microservice.customer.Exception;

public class CustomerNotFoundException  extends RuntimeException{
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
