package com.example.ECommerceBackendSpringBoot.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int orderId;
    String orderNo;
    @CreationTimestamp
    Date orderDate;
    int totalCost;
    String cardUsed;

    @ManyToOne
    @JoinColumn
    User user;

    @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL)
    List<Item> items = new ArrayList<>();
}
