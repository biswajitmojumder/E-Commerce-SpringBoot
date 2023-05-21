package com.example.ECommerceBackendSpringBoot.service;

import com.example.ECommerceBackendSpringBoot.Dto.Request.CardRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.CardResponseDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.CardTypeResponseDto;
import com.example.ECommerceBackendSpringBoot.enums.CardType;
import com.example.ECommerceBackendSpringBoot.exception.NoCardFoundOfTypeVisaException;
import com.example.ECommerceBackendSpringBoot.exception.NoUserFoundWithThatDateException;
import com.example.ECommerceBackendSpringBoot.exception.UserNotFoundException;

import java.util.Date;
import java.util.List;

public interface CardService {
    String addCard(CardRequestDto cardRequestDto) throws UserNotFoundException;

    CardTypeResponseDto getTheMostUsedCard();

    List<CardResponseDto> getAllTheVisaCardsUsers() throws NoCardFoundOfTypeVisaException;

    List<CardResponseDto> getAllTheCardsWhoesExpiryIsGreaterThan(String cardType, Date expiryDate);
}
