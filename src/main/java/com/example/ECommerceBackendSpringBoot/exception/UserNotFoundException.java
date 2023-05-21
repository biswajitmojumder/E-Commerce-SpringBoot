package com.example.ECommerceBackendSpringBoot.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String enterTheValidEmailId) {
        super(enterTheValidEmailId);
    }
}
