package com.juandavyc.cards.mapper;

import com.juandavyc.cards.constants.CardConstants;
import com.juandavyc.cards.dto.CardDto;
import com.juandavyc.cards.entity.Card;

public class CardMapper {

    public static CardDto toDto(Card card) {
        CardDto newCard = new CardDto();

        newCard.setCardNumber(card.getCardNumber());
        newCard.setMobileNumber(card.getMobileNumber());
        newCard.setCardType(card.getCardType());
        newCard.setTotalLimit(card.getTotalLimit());
        newCard.setAmountUsed(card.getAmountUsed());
        newCard.setAvailableAmount(card.getAvailableAmount());

        return newCard;
    }

    public static Card toEntity(CardDto cardDto, Card card){
        Card toUpdateCard = new Card();

        card.setCardNumber(cardDto.getCardNumber());
        card.setMobileNumber(cardDto.getMobileNumber());
        card.setCardType(cardDto.getCardType());
        card.setTotalLimit(cardDto.getTotalLimit());
        card.setAmountUsed(cardDto.getAmountUsed());
        card.setAvailableAmount(cardDto.getAvailableAmount());

        return toUpdateCard;
    }
}
