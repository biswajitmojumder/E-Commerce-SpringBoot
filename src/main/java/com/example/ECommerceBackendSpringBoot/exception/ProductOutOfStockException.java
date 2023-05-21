package com.example.ECommerceBackendSpringBoot.exception;

public class ProductOutOfStockException extends Exception {
    public ProductOutOfStockException(String s) {
        super(s);
    }
}
