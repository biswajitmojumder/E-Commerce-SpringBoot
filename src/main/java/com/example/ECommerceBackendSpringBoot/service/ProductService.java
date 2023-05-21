package com.example.ECommerceBackendSpringBoot.service;

import com.example.ECommerceBackendSpringBoot.Dto.Request.ProductRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.ProductResponseDto;
import com.example.ECommerceBackendSpringBoot.exception.ProductNotFoundException;
import com.example.ECommerceBackendSpringBoot.exception.SellerNotFoundException;

import java.util.List;

public interface ProductService {
    String addProduct(ProductRequestDto productRequestDto) throws SellerNotFoundException;

    List<ProductResponseDto> getAllProductsByCategory(String category);

    List<ProductResponseDto> getAllProductsByPriceCategory(int price, String category);

    List<ProductResponseDto> getAllProductsBySellerEmailId(String emailId) throws SellerNotFoundException;

    String deleteProductBySellerId(int sellerId,int productId) throws SellerNotFoundException, ProductNotFoundException;


    List<ProductResponseDto> getTopFiveCheapestProducts();

    List<ProductResponseDto> getTopFiveCostliestProducts();

    List<ProductResponseDto> getAllOutOfStockProducts();

    List<ProductResponseDto> getAllAvailableProducts();

    List<ProductResponseDto> getAllProductsWhoHaveQuantityLessThan(int quantity);

    ProductResponseDto getCheapestProductByCategory(String category);

    ProductResponseDto getCostliestProductByCategory(String category);
}
