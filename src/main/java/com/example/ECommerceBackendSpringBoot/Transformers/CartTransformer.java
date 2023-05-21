package com.example.ECommerceBackendSpringBoot.Transformers;

import com.example.ECommerceBackendSpringBoot.Dto.Response.CartResponseDto;
import com.example.ECommerceBackendSpringBoot.entity.Cart;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CartTransformer {
    public static CartResponseDto cartToCartResponseDto(Cart cart) {
        return CartResponseDto.builder()
                .cartTotal(cart.getTotalCost())
                .numberOfItems(cart.getNumberOfItems())
                .userName(cart.getUser().getUserName())
                .build();
    }
}
