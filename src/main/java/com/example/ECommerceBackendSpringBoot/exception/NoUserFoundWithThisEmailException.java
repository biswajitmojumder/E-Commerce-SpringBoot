package com.example.ECommerceBackendSpringBoot.exception;

public class NoUserFoundWithThisEmailException extends Exception {
    public NoUserFoundWithThisEmailException(String s) {
        super(s);
    }
}
