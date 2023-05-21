package com.example.ECommerceBackendSpringBoot.repository;

import com.example.ECommerceBackendSpringBoot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmailId(String emailId);

    User findByMobNo(String mobNo);

    @Query(value = "select * from user u where u.age > :age",nativeQuery = true)
    List<User> getAllUsersGreaterThanAge(int age);
}
