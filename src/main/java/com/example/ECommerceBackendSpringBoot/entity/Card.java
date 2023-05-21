package com.example.ECommerceBackendSpringBoot.entity;

import com.example.ECommerceBackendSpringBoot.enums.CardType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int cardId;
    @Column(unique = true,nullable = false)
    String cardNo;
    int cardCvv;
    Date cardExpiryDate;
    @Enumerated(EnumType.STRING)
    CardType cardType;

    @ManyToOne
    @JoinColumn
    User user;
}
