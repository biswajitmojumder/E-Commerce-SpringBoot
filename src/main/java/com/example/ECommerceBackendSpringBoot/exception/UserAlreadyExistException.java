package com.example.ECommerceBackendSpringBoot.exception;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String s) {
        super(s);
    }
}
