package com.example.ECommerceBackendSpringBoot.controller;

import com.example.ECommerceBackendSpringBoot.Dto.Request.CheckOutCartRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Request.ItemRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.CartResponseDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.OrderResponseDto;
import com.example.ECommerceBackendSpringBoot.entity.Item;
import com.example.ECommerceBackendSpringBoot.exception.ProductNotFoundException;
import com.example.ECommerceBackendSpringBoot.exception.ProductOutOfStockException;
import com.example.ECommerceBackendSpringBoot.exception.UserNotFoundException;
import com.example.ECommerceBackendSpringBoot.service.CartService;
import com.example.ECommerceBackendSpringBoot.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
public class CartController {
    @Autowired
    ItemService itemService;
    @Autowired
    CartService cartService;
    @PostMapping("/add")
    public ResponseEntity addToCart(@RequestBody ItemRequestDto itemRequestDto){
        try{
            Item savedItem = itemService.addItem(itemRequestDto);
            CartResponseDto cartResponseDto = cartService.addToCart(itemRequestDto.getUserId(),savedItem);
            return new ResponseEntity<>(cartResponseDto, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/checkout")
    public OrderResponseDto checkOutCart(@RequestBody CheckOutCartRequestDto checkOutCartRequestDto) throws Exception {
        return cartService.checkOutCart(checkOutCartRequestDto);
    }
    @DeleteMapping("/remove/{userId}/{itemId}")
    public String removeFromCart(@PathVariable("userId") int userId,@PathVariable("itemId") int itemId) throws UserNotFoundException {
        return cartService.removeFromCart(userId,itemId);
    }
}
