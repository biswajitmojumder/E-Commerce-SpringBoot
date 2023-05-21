package com.example.ECommerceBackendSpringBoot.Dto.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ItemResponseDto {
    String productName;
    int pricePerItem;
    int totalPrice;
    int quantity;

}
