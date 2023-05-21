package com.example.ECommerceBackendSpringBoot.repository;

import com.example.ECommerceBackendSpringBoot.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Integer> {
    @Query(value = "SELECT * FROM orders o ORDER BY o.total_cost DESC LIMIT 1",nativeQuery = true)
    Orders getTheHighestValueOrderEver();

    @Query(value = "SELECT * FROM orders o WHERE o.user_user_id = :userId ORDER BY order_date DESC LIMIT 5",nativeQuery = true)
    List<Orders> getRecent5OrdersOfUser(int userId);
}
