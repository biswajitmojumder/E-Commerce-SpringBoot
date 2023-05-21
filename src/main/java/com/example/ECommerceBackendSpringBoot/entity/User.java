package com.example.ECommerceBackendSpringBoot.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int userId;
    String userName;
    @Column(unique = true)
    String emailId;
    int age;
    @Column(unique = true)
    String mobNo;
    String address;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Card> cards = new ArrayList<>();

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    Cart cart;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Orders> orders = new ArrayList<>();
}
