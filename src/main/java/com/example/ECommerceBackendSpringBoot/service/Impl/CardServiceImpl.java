package com.example.ECommerceBackendSpringBoot.service.Impl;

import com.example.ECommerceBackendSpringBoot.Dto.Request.CardRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.CardResponseDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.CardTypeResponseDto;
import com.example.ECommerceBackendSpringBoot.Transformers.CardTransformer;
import com.example.ECommerceBackendSpringBoot.entity.Card;
import com.example.ECommerceBackendSpringBoot.entity.User;
import com.example.ECommerceBackendSpringBoot.enums.CardType;
import com.example.ECommerceBackendSpringBoot.exception.NoCardFoundOfTypeVisaException;
import com.example.ECommerceBackendSpringBoot.exception.NoUserFoundWithThatDateException;
import com.example.ECommerceBackendSpringBoot.exception.UserNotFoundException;
import com.example.ECommerceBackendSpringBoot.repository.CardRepository;
import com.example.ECommerceBackendSpringBoot.repository.UserRepository;
import com.example.ECommerceBackendSpringBoot.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class CardServiceImpl implements CardService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CardRepository cardRepository;

    @Override
    public String addCard(CardRequestDto cardRequestDto) throws UserNotFoundException {
        User user = userRepository.findByMobNo(cardRequestDto.getMobNo());
        if(user == null) throw new UserNotFoundException("user does not exists");

        Card card = CardTransformer.cardRequestDtoToCard(cardRequestDto);
        card.setUser(user);
        user.getCards().add(card);

        userRepository.save(user);
        return "card added successfully";
    }

    @Override
    public CardTypeResponseDto getTheMostUsedCard() {
        List<Object[]> res = cardRepository.getTheMostUsedCard();
        CardTypeResponseDto cardTypeResponseDto = new CardTypeResponseDto();
        if(!res.isEmpty()) {
            Object[] row = res.get(0);
            CardType maxCardType = (CardType) row[0];
            cardTypeResponseDto.setCardType(maxCardType);
        }
        return cardTypeResponseDto;
    }

    @Override
    public List<CardResponseDto> getAllTheVisaCardsUsers() throws NoCardFoundOfTypeVisaException {
        try{
            List<Card> cards = cardRepository.findByCardType(CardType.VISA);
            List<CardResponseDto> cardResponseDtoList = new ArrayList<>();
            for (Card card: cards) {
                CardResponseDto cardResponseDto = CardTransformer.cardToCardResponse(card);
                cardResponseDtoList.add(cardResponseDto);
            }
            return cardResponseDtoList;
        }
        catch (Exception e) {
            throw new NoCardFoundOfTypeVisaException("there are no cards of type visa");
        }
    }

    @Override
    public List<CardResponseDto> getAllTheCardsWhoesExpiryIsGreaterThan(String cardType, Date expiryDate) {
        List<Card> cards = cardRepository.getAllTheCardsWhoesExpiryIsGreaterThan(cardType,expiryDate);
        List<CardResponseDto> cardResponseDtoList = new ArrayList<>();
        for (Card card:cards) {
            CardResponseDto cardResponseDto = CardTransformer.cardToCardResponse(card);
            cardResponseDtoList.add(cardResponseDto);
        }
        return cardResponseDtoList;
    }
}
