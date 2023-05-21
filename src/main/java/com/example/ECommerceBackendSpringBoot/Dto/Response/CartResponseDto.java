package com.example.ECommerceBackendSpringBoot.Dto.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CartResponseDto {
    Integer cartTotal;
    Integer numberOfItems;
    String userName;
    List<ItemResponseDto> items;
}
