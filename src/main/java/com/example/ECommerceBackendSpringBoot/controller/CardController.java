package com.example.ECommerceBackendSpringBoot.controller;

import com.example.ECommerceBackendSpringBoot.Dto.Request.CardRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.CardResponseDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.CardTypeResponseDto;
import com.example.ECommerceBackendSpringBoot.enums.CardType;
import com.example.ECommerceBackendSpringBoot.exception.NoCardFoundOfTypeVisaException;
import com.example.ECommerceBackendSpringBoot.exception.UserNotFoundException;
import com.example.ECommerceBackendSpringBoot.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    CardService cardService;
    @PostMapping("/add")
    public String addCard(@RequestBody CardRequestDto cardRequestDto) throws UserNotFoundException {
        return cardService.addCard(cardRequestDto);
    }
    @GetMapping("/get-most-used")
    public CardTypeResponseDto getTheMostUsedCard(){
        return cardService.getTheMostUsedCard();
    }

    @GetMapping("/get-all-visa-cards")
    public List<CardResponseDto> getAllTheVisaCardsUsers() throws NoCardFoundOfTypeVisaException {
        return cardService.getAllTheVisaCardsUsers();
    }

    @GetMapping("/get-all-cards-greater-expiry")
    public List<CardResponseDto> getAllTheCardsWhoesExpiryIsGreaterThan(@RequestParam("cardType") String cardType, @RequestParam("expiryDate") Date expiryDate) {
        return cardService.getAllTheCardsWhoesExpiryIsGreaterThan(cardType,expiryDate);
    }
}
