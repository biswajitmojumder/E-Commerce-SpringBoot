package com.example.ECommerceBackendSpringBoot.Dto.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequestDto {
    int userId;
    int productId;
    int requiredQuantity;
    String cardNo;
    int cvv;
}
