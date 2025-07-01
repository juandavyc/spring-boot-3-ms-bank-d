package com.juandavyc.cards.service.impl;

import com.juandavyc.cards.constants.CardConstants;
import com.juandavyc.cards.dto.CardDto;
import com.juandavyc.cards.entity.Card;
import com.juandavyc.cards.exception.CardAlreadyExistsException;
import com.juandavyc.cards.exception.ResourceNotFoundException;
import com.juandavyc.cards.mapper.CardMapper;
import com.juandavyc.cards.respository.CardRepository;
import com.juandavyc.cards.service.ICardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ICardServiceImpl implements ICardService {

    private final CardRepository cardRepository;

    @Override
    public Long createCard(String mobileNumber) {

        boolean exists = cardRepository.existsByMobileNumber(mobileNumber);
        if (exists) {
            throw new CardAlreadyExistsException("Card already registered with mobileNumber: " + mobileNumber);
        }
        return cardRepository
                .save(getCardEntity(mobileNumber))
                .getCardId();
    }

    @Override
    public CardDto fetchCardByCardId(Long cardId) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(()-> new ResourceNotFoundException("Card", "cardId", cardId.toString()));

        return CardMapper.toDto(card);
    }

    @Override
    public CardDto fetchCardByMobileNumber(String mobileNumber) {

        Card card = cardRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()-> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));

        return CardMapper.toDto(card);
    }

    @Override
    public boolean updateCard(CardDto cardDto) {

        Card card = cardRepository.findByMobileNumber(cardDto.getMobileNumber())
                .orElseThrow(()-> new ResourceNotFoundException("Card", "mobileNumber", cardDto.getMobileNumber()));

        CardMapper.toEntity(cardDto, card);
        cardRepository.save(card);

        return true;
    }

    public boolean deleteCard(String mobileNumber) {
        Card cards = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardRepository.deleteById(cards.getCardId());
        return true;
    }


    private Card getCardEntity(String mobileNumber){
        Card newCard = new Card();
        long cardNumber = 100000000000L + new Random().nextLong(1000000);

        newCard.setMobileNumber(mobileNumber);
        newCard.setCardNumber(Long.toString(cardNumber));
        newCard.setCardType(CardConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardConstants.NEW_CARD_LIMIT);

        return newCard;
    }

}
