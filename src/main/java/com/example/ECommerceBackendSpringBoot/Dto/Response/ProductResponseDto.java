package com.example.ECommerceBackendSpringBoot.Dto.Response;

import com.example.ECommerceBackendSpringBoot.enums.ProductCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProductResponseDto {
    String productName;
    int price;
    int quantity;
    ProductCategory productCategory;
    String sellerName;
}
