package com.example.ECommerceBackendSpringBoot.Dto.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponseDto {
    String orderNo;
    int totalValue;
    Date orderDate;
    String cardUsed;
    List<ItemResponseDto> items;
    String userName;
}
