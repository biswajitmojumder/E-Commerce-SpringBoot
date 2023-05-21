package com.example.ECommerceBackendSpringBoot.Dto.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SellerResponseDto {
    String sellerName;
    String emailId;
    int age;
    String mobNo;
}
