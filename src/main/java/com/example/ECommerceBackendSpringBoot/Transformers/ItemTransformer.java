package com.example.ECommerceBackendSpringBoot.Transformers;

import com.example.ECommerceBackendSpringBoot.Dto.Request.ItemRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.ItemResponseDto;
import com.example.ECommerceBackendSpringBoot.entity.Item;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemTransformer {
    public static Item itemRequestDtoToItem(ItemRequestDto itemRequestDto) {
        return Item.builder()
                .requiredQuantity(itemRequestDto.getRequiredQuantity())
                .build();
    }
    public static ItemResponseDto itemToItemResponseDto(Item item) {
        return ItemResponseDto.builder()
                .pricePerItem(item.getProduct().getPrice())
                .productName(item.getProduct().getProductName())
                .totalPrice(item.getProduct().getPrice() * item.getRequiredQuantity())
                .quantity(item.getRequiredQuantity())
                .build();
    }
}
