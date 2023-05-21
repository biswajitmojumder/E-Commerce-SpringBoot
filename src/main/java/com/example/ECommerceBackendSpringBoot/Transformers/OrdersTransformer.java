package com.example.ECommerceBackendSpringBoot.Transformers;

import com.example.ECommerceBackendSpringBoot.Dto.Response.OrderResponseDto;
import com.example.ECommerceBackendSpringBoot.entity.Orders;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrdersTransformer {
    public static OrderResponseDto orderToOrderResponseDto(Orders orders) {
        return OrderResponseDto.builder()
                .cardUsed(orders.getCardUsed())
                .orderNo(orders.getOrderNo())
                .totalValue(orders.getTotalCost())
                .orderDate(orders.getOrderDate())
                .userName(orders.getUser().getUserName())
                .build();
    }
}
