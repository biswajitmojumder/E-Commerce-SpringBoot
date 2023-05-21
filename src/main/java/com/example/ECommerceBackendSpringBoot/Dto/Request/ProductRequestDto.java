package com.example.ECommerceBackendSpringBoot.Dto.Request;

import com.example.ECommerceBackendSpringBoot.enums.ProductCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProductRequestDto {
    String productName;
    int price;
    int quantity;
    ProductCategory productCategory;
    int sellerId;
}
