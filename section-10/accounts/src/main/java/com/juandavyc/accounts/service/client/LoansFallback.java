package com.juandavyc.accounts.service.client;

import com.juandavyc.accounts.dto.LoanDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient{

    @Override
    public ResponseEntity<LoanDto> fetchLoanDetails(String mobileNumber) {

        return null;
    }
}
