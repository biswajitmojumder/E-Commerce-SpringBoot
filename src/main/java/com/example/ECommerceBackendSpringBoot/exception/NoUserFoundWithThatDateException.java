package com.example.ECommerceBackendSpringBoot.exception;

public class NoUserFoundWithThatDateException extends Exception {
    public NoUserFoundWithThatDateException(String s) {
        super(s);
    }
}
