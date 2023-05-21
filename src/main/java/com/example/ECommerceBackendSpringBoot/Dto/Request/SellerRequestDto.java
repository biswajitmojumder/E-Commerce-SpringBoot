package com.example.ECommerceBackendSpringBoot.Dto.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SellerRequestDto {
    String sellerName;
    String emailId;
    int age;
    String mobNo;
}
