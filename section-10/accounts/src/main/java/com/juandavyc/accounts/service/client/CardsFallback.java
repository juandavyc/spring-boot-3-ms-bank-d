package com.juandavyc.accounts.service.client;

import com.juandavyc.accounts.dto.CardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient{

    @Override
    public ResponseEntity<CardDto> fetchCardDetails(String mobileNumber) {
        return null;
    }
}
