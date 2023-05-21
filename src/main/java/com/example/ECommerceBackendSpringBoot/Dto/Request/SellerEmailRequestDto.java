package com.example.ECommerceBackendSpringBoot.Dto.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SellerEmailRequestDto {
    String emailId;
}
