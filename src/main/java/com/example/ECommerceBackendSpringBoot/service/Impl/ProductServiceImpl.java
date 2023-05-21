package com.example.ECommerceBackendSpringBoot.service.Impl;

import com.example.ECommerceBackendSpringBoot.Dto.Request.ProductRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.ProductResponseDto;
import com.example.ECommerceBackendSpringBoot.Transformers.ProductTransformer;
import com.example.ECommerceBackendSpringBoot.entity.Item;
import com.example.ECommerceBackendSpringBoot.entity.Product;
import com.example.ECommerceBackendSpringBoot.entity.Seller;
import com.example.ECommerceBackendSpringBoot.enums.ProductStatus;
import com.example.ECommerceBackendSpringBoot.exception.ProductNotFoundException;
import com.example.ECommerceBackendSpringBoot.exception.SellerNotFoundException;
import com.example.ECommerceBackendSpringBoot.repository.ProductRepository;
import com.example.ECommerceBackendSpringBoot.repository.SellerRepository;
import com.example.ECommerceBackendSpringBoot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    SellerRepository sellerRepository;
    @Override
    public String addProduct(ProductRequestDto productRequestDto) throws SellerNotFoundException {
        Seller seller;
        try {
            seller = sellerRepository.findById(productRequestDto.getSellerId()).get();
        }
        catch (Exception e) {
            throw new SellerNotFoundException("there is not seller with this id");
        }
        Product product = ProductTransformer.productRequestDtoToProduct(productRequestDto);
        product.setProductStatus(ProductStatus.AVAILABLE);
        product.setSeller(seller);

        seller.getProducts().add(product);
        sellerRepository.save(seller);

        return "product added successfully";
    }

    @Override
    public List<ProductResponseDto> getAllProductsByCategory(String category) {
        List<Product> products = productRepository.getAllProductsByCategory(category);
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product: products) {
            ProductResponseDto productResponseDto = ProductTransformer.productToProductResponseDto(product);
            productResponseDtoList.add(productResponseDto);
        }
        return productResponseDtoList;
    }

    @Override
    public List<ProductResponseDto> getAllProductsByPriceCategory(int price, String category) {
        List<Product> products = productRepository.getAllProductsByPriceCategory(price,category);
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : products) {
            ProductResponseDto productResponseDto = ProductTransformer.productToProductResponseDto(product);
            productResponseDtoList.add(productResponseDto);
        }
        return productResponseDtoList;
    }

    @Override
    public List<ProductResponseDto> getAllProductsBySellerEmailId(String emailId) throws SellerNotFoundException {
        Seller seller;
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        try{
            seller = sellerRepository.findByEmailId(emailId);
        }
        catch (Exception e) {
            throw new SellerNotFoundException("there is no seller with this email id");
        }
        List<Product> products = seller.getProducts();

        for (Product product : products) {
            ProductResponseDto productResponseDto = ProductTransformer.productToProductResponseDto(product);
            productResponseDtoList.add(productResponseDto);
        }
        return productResponseDtoList;
    }

    @Override
    public String deleteProductBySellerId(int sellerId,int productId) throws SellerNotFoundException, ProductNotFoundException {
        Seller seller;
        Product product;
        try{
            seller = sellerRepository.findById(sellerId).get();
        }
        catch (Exception e) {
            throw new SellerNotFoundException("there is no seller with this sellerId");
        }
        try{
            product = productRepository.findById(productId).get();
        }
        catch (Exception e) {
            throw new ProductNotFoundException("there is no product with this productId");
        }
        productRepository.delete(product);
        sellerRepository.save(seller);
        return "product deleted successfully";
    }

    @Override
    public List<ProductResponseDto> getTopFiveCheapestProducts() {
        List<Product> products = productRepository.findTop5CheapestProducts();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : products) {
            ProductResponseDto productResponseDto = ProductTransformer.productToProductResponseDto(product);
            productResponseDtoList.add(productResponseDto);
        }

        return productResponseDtoList;
    }

    @Override
    public List<ProductResponseDto> getTopFiveCostliestProducts() {
        List<Product> products = productRepository.findTop5CostliestProducts();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : products) {
            ProductResponseDto productResponseDto = ProductTransformer.productToProductResponseDto(product);
            productResponseDtoList.add(productResponseDto);
        }

        return productResponseDtoList;
    }

    @Override
    public List<ProductResponseDto> getAllOutOfStockProducts() {
        List<Product> products = productRepository.findAllOutOfStockProducts();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : products) {
            ProductResponseDto productResponseDto = ProductTransformer.productToProductResponseDto(product);
            productResponseDtoList.add(productResponseDto);
        }

        return productResponseDtoList;
    }

    @Override
    public List<ProductResponseDto> getAllAvailableProducts() {
        List<Product> products = productRepository.getAllAvailableProducts();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : products) {
            ProductResponseDto productResponseDto = ProductTransformer.productToProductResponseDto(product);
            productResponseDtoList.add(productResponseDto);
        }

        return productResponseDtoList;
    }

    @Override
    public List<ProductResponseDto> getAllProductsWhoHaveQuantityLessThan(int quantity) {
        List<Product> products = productRepository.getAllProductsWhoHaveQuantityLessThan(quantity);
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : products) {
            ProductResponseDto productResponseDto = ProductTransformer.productToProductResponseDto(product);
            productResponseDtoList.add(productResponseDto);
        }

        return productResponseDtoList;
    }

    @Override
    public ProductResponseDto getCheapestProductByCategory(String category) {
        Product product = productRepository.getCheapestProductByCategory(category);
        return ProductTransformer.productToProductResponseDto(product);
    }

    @Override
    public ProductResponseDto getCostliestProductByCategory(String category) {
        Product product = productRepository.getCostliestProductByCategory(category);
        return ProductTransformer.productToProductResponseDto(product);
    }

    public void decreaseProductQuantity(Item item) throws Exception {

        Product product = item.getProduct();
        int quantity = item.getRequiredQuantity();
        int currentQuantity = product.getQuantity();
        if (quantity > currentQuantity) {
            throw new Exception("Out of stock");
        }
        product.setQuantity(currentQuantity - quantity);
        if (product.getQuantity() == 0) {
            product.setProductStatus(ProductStatus.OUT_OF_STOCK);
        }
    }
}
