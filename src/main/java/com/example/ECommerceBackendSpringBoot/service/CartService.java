package com.example.ECommerceBackendSpringBoot.service;

import com.example.ECommerceBackendSpringBoot.Dto.Request.CheckOutCartRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.CartResponseDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.OrderResponseDto;
import com.example.ECommerceBackendSpringBoot.entity.Item;
import com.example.ECommerceBackendSpringBoot.exception.InvalidCardException;
import com.example.ECommerceBackendSpringBoot.exception.UserNotFoundException;

public interface CartService {
    CartResponseDto addToCart(int userId, Item savedItem);

    OrderResponseDto checkOutCart(CheckOutCartRequestDto checkOutCartRequestDto) throws Exception;

    String removeFromCart(int userId, int itemId) throws UserNotFoundException;
}
