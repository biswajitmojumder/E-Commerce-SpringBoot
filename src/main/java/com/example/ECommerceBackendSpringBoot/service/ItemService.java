package com.example.ECommerceBackendSpringBoot.service;

import com.example.ECommerceBackendSpringBoot.Dto.Request.ItemRequestDto;
import com.example.ECommerceBackendSpringBoot.entity.Item;
import com.example.ECommerceBackendSpringBoot.exception.ProductNotFoundException;
import com.example.ECommerceBackendSpringBoot.exception.ProductOutOfStockException;
import com.example.ECommerceBackendSpringBoot.exception.UserNotFoundException;

public interface ItemService {
   Item addItem(ItemRequestDto itemRequestDto) throws UserNotFoundException, ProductNotFoundException, ProductOutOfStockException;
}
