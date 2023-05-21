package com.example.ECommerceBackendSpringBoot.Transformers;

import com.example.ECommerceBackendSpringBoot.Dto.Request.CardRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.CardResponseDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.CardTypeResponseDto;
import com.example.ECommerceBackendSpringBoot.entity.Card;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CardTransformer {
    public static Card cardRequestDtoToCard(CardRequestDto cardRequestDto) {
        return Card.builder()
                .cardNo(cardRequestDto.getCardNo())
                .cardCvv(cardRequestDto.getCardCvv())
                .cardExpiryDate(cardRequestDto.getCardExpiryDate())
                .cardType(cardRequestDto.getCardType())
                .build();
    }
    public static CardResponseDto cardToCardResponse (Card card) {
        return CardResponseDto.builder()
                .cardNo(card.getCardNo())
                .cardType(card.getCardType())
                .userName(card.getUser().getUserName())
                .build();
    }
    public static CardTypeResponseDto cardToCardTypeResponseDto(Card card) {
        return CardTypeResponseDto.builder()
                .cardType(card.getCardType())
                .build();
    }
}
