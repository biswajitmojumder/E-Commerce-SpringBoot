package com.example.ECommerceBackendSpringBoot.service;

import com.example.ECommerceBackendSpringBoot.Dto.Request.OrderRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.OrderResponseDto;
import com.example.ECommerceBackendSpringBoot.exception.OrderDoesNotExistsException;
import com.example.ECommerceBackendSpringBoot.exception.UserNotFoundException;

import java.util.List;

public interface OrdersService {
    OrderResponseDto placeDirectOrder(OrderRequestDto orderRequestDto) throws Exception;

    List<OrderResponseDto> getAllOrdersOfAUser(int userId) throws UserNotFoundException;

    List<OrderResponseDto> getRecentFiveOrdersOfUser(int userId);

    String deleteOrderOfAUser(int orderId) throws OrderDoesNotExistsException;

    OrderResponseDto getTheHighestValueOrderEver();
}
