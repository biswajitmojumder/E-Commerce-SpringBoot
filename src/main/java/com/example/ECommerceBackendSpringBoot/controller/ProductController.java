package com.example.ECommerceBackendSpringBoot.controller;

import com.example.ECommerceBackendSpringBoot.Dto.Request.ProductRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.ProductResponseDto;
import com.example.ECommerceBackendSpringBoot.exception.ProductNotFoundException;
import com.example.ECommerceBackendSpringBoot.exception.SellerNotFoundException;
import com.example.ECommerceBackendSpringBoot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    ProductService productService;
    @PostMapping("/add")
    public String addProduct(@RequestBody ProductRequestDto productRequestDto) throws SellerNotFoundException {
        return productService.addProduct(productRequestDto);
    }
    @GetMapping("/get-products/{category}")
    public List<ProductResponseDto> getAllProductsByCategory(@PathVariable("category") String category) {
        return productService.getAllProductsByCategory(category);
    }
    @GetMapping("/get-by/{price}/{category}")
    public List<ProductResponseDto> getAllProductsByPriceCategory(@PathVariable("price") int price,@PathVariable("category") String category) {
        return productService.getAllProductsByPriceCategory(price,category);
    }
    @GetMapping("/get-all-by/{emailId}")
    public List<ProductResponseDto> getAllProductsBySellerEmailId(@PathVariable("emailId") String emailId) throws SellerNotFoundException {
        return productService.getAllProductsBySellerEmailId(emailId);
    }
    @DeleteMapping("/delete-by/{sellerId}/{productId}")
    public String deleteProductBySellerId(@PathVariable("sellerId") int sellerId,@PathVariable("productId") int productId) throws SellerNotFoundException, ProductNotFoundException {
        return productService.deleteProductBySellerId(sellerId,productId);
    }
    @GetMapping("/get-top-5-cheapest-products")
    public List<ProductResponseDto> getTopFiveCheapestProducts(){
        return productService.getTopFiveCheapestProducts();
    }
    @GetMapping("/get-top-5-costliest-products")
    public List<ProductResponseDto> getTopFiveCostliestProducts(){
        return productService.getTopFiveCostliestProducts();
    }
    @GetMapping("/get-all-outOfStock-products")
    public List<ProductResponseDto> getAllOutOfStockProducts(){
        return productService.getAllOutOfStockProducts();
    }
    @GetMapping("/get-all-available-products")
    public List<ProductResponseDto> getAllAvailableProducts(){
        return productService.getAllAvailableProducts();
    }
    @GetMapping("/get-all-products/{quantity}")
    public List<ProductResponseDto> getAllProductsWhoHaveQuantityLessThan(@PathVariable("quantity") int quantity){
        return productService.getAllProductsWhoHaveQuantityLessThan(quantity);
    }
    @GetMapping("/get-cheapest-product-by/{category}")
    public ProductResponseDto getCheapestProductByCategory(@PathVariable("category") String category) {
        return productService.getCheapestProductByCategory(category);
    }
    @GetMapping("/get-costliest-product-by/{category}")
    public ProductResponseDto getCostliestProductByCategory(@PathVariable("category") String category) {
        return productService.getCostliestProductByCategory(category);
    }

}
