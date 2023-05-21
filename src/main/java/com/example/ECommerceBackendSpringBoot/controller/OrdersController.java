package com.example.ECommerceBackendSpringBoot.controller;

import com.example.ECommerceBackendSpringBoot.Dto.Request.OrderRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.OrderResponseDto;
import com.example.ECommerceBackendSpringBoot.exception.OrderDoesNotExistsException;
import com.example.ECommerceBackendSpringBoot.exception.UserNotFoundException;
import com.example.ECommerceBackendSpringBoot.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    OrdersService ordersService;
    @PostMapping("/place")
    public OrderResponseDto placeDirectOrder(@RequestBody OrderRequestDto orderRequestDto) throws Exception {
        return ordersService.placeDirectOrder(orderRequestDto);
    }
    @GetMapping("/get-all/{userId}")
    public List<OrderResponseDto> getAllOrdersOfAUser(@PathVariable("userId") int userId) throws UserNotFoundException {
        return ordersService.getAllOrdersOfAUser(userId);
    }
    @GetMapping("/get-recent/{userId}")
    public List<OrderResponseDto> getRecentFiveOrdersOfUser(@PathVariable("userId") int userId){
        return ordersService.getRecentFiveOrdersOfUser(userId);
    }
    @DeleteMapping("/delete-order/{orderId}")
    public String deleteOrderOfAUser(@PathVariable("orderId") int orderId) throws OrderDoesNotExistsException {
        return ordersService.deleteOrderOfAUser(orderId);
    }
    @GetMapping("/get-the-highest-value-order")
    public OrderResponseDto getTheHighestValueOrderEver() {
        return ordersService.getTheHighestValueOrderEver();
    }
}
