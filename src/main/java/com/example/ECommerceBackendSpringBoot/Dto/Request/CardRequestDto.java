package com.example.ECommerceBackendSpringBoot.Dto.Request;

import com.example.ECommerceBackendSpringBoot.enums.CardType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CardRequestDto {
    String mobNo;
    String cardNo;
    int cardCvv;
    Date cardExpiryDate;
    CardType cardType;
}
