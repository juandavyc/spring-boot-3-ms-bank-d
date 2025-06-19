package com.juandavyc.cards.service;

import com.juandavyc.cards.dto.CardDto;

public interface ICardService {

    Long createCard(String mobileNumber);

    CardDto fetchCardByCardId(Long cardId);

    CardDto fetchCardByMobileNumber(String mobileNumber);

    boolean updateCard(CardDto cardDto);

    boolean deleteCard(String mobileNumber);

}


