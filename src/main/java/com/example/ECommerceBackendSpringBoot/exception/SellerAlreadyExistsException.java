package com.example.ECommerceBackendSpringBoot.exception;

public class SellerAlreadyExistsException extends Exception {
    public SellerAlreadyExistsException(String sellerAlreadyExists) {
        super(sellerAlreadyExists);
    }
}
