package com.juandavyc.accounts.service.impl;

import com.juandavyc.accounts.dto.*;
import com.juandavyc.accounts.entity.AccountsEntity;
import com.juandavyc.accounts.entity.CustomerEntity;
import com.juandavyc.accounts.exception.ResourceNotFoundException;
import com.juandavyc.accounts.mapper.AccountsMapper;
import com.juandavyc.accounts.mapper.CustomerMapper;
import com.juandavyc.accounts.repository.AccountsRepository;
import com.juandavyc.accounts.repository.CustomerRepository;
import com.juandavyc.accounts.service.ICustomersService;
import com.juandavyc.accounts.service.client.CardsFeignClient;
import com.juandavyc.accounts.service.client.LoansFeignClient;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ICustomersServiceImpl implements ICustomersService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {

        CustomerEntity customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        AccountsEntity account = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());

        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(account, new AccountsDto()));

        ResponseEntity<LoanDto> loanDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);

        customerDetailsDto.setLoansDto(loanDtoResponseEntity.getBody());


        ResponseEntity<CardDto> cardDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardDtoResponseEntity.getBody());

        return customerDetailsDto;
    }

}
