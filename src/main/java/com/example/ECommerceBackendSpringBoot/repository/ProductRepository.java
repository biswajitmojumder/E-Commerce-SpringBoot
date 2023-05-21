package com.example.ECommerceBackendSpringBoot.repository;

import com.example.ECommerceBackendSpringBoot.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query(value = "select * from product p where p.product_category = :category",nativeQuery = true)
    List<Product> getAllProductsByCategory(String category);

    @Query(value = "select * from product p where p.price = :price and p.product_category = :category",nativeQuery = true)
    List<Product> getAllProductsByPriceCategory(int price, String category);

    @Query(value = "SELECT * FROM product p ORDER BY p.price ASC LIMIT 5", nativeQuery = true)
    List<Product> findTop5CheapestProducts();

    @Query(value = "SELECT * FROM product p ORDER BY p.price DESC LIMIT 5", nativeQuery = true)
    List<Product> findTop5CostliestProducts();

    @Query(value = "select * from product p where p.product_status = 'OUT_OF_STOCK'",nativeQuery = true)
    List<Product> findAllOutOfStockProducts();

    @Query(value = "select * from product p where p.product_status = 'AVAILABLE'",nativeQuery = true)
    List<Product> getAllAvailableProducts();

    @Query(value = "select * from product p where p.quantity < :quantity",nativeQuery = true)
    List<Product> getAllProductsWhoHaveQuantityLessThan(int quantity);

    @Query(value = "SELECT * FROM product p WHERE p.product_category = ?1 ORDER BY p.price ASC LIMIT 1", nativeQuery = true)
    Product getCheapestProductByCategory(String category);

    @Query(value = "SELECT * FROM product p WHERE p.product_category = ?1 ORDER BY p.price DESC LIMIT 1", nativeQuery = true)
    Product getCostliestProductByCategory(String category);
}
