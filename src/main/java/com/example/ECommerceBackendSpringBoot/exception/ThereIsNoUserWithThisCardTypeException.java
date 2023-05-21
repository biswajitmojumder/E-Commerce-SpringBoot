package com.example.ECommerceBackendSpringBoot.exception;

public class ThereIsNoUserWithThisCardTypeException extends Exception {
    public ThereIsNoUserWithThisCardTypeException(String s) {
        super(s);
    }
}
