package com.example.ECommerceBackendSpringBoot.exception;

public class OrderDoesNotExistsException extends Exception {
    public OrderDoesNotExistsException(String s) {
        super(s);
    }
}
