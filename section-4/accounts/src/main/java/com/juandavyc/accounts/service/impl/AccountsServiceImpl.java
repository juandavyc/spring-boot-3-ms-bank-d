package com.juandavyc.accounts.service.impl;

import com.juandavyc.accounts.constants.AccountConstants;
import com.juandavyc.accounts.dto.AccountsDto;
import com.juandavyc.accounts.dto.CustomerDto;
import com.juandavyc.accounts.entity.AccountsEntity;
import com.juandavyc.accounts.entity.CustomerEntity;
import com.juandavyc.accounts.exception.CustomerAlreadyExistsException;
import com.juandavyc.accounts.exception.ResourceNotFoundException;
import com.juandavyc.accounts.mapper.AccountsMapper;
import com.juandavyc.accounts.mapper.CustomerMapper;
import com.juandavyc.accounts.repository.AccountsRepository;
import com.juandavyc.accounts.repository.CustomerRepository;
import com.juandavyc.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.netty.NettyWebServer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Long createAccount(CustomerDto customerDto) {

        final var existsByMobileNumber = customerRepository.existsByMobileNumber(
                customerDto.getMobileNumber()
        );

        if (existsByMobileNumber) {
            throw new CustomerAlreadyExistsException("The number is already in use" + customerDto.getMobileNumber());
        }

        CustomerEntity customerEntity = CustomerMapper
                .mapToCustomer(customerDto, new CustomerEntity());

//        customerEntity.setCreatedAt(LocalDateTime.now());
//        customerEntity.setCreatedBy("system");

        final var savedCustomer = customerRepository.save(customerEntity);
        final var toSaveAccount = createNewAccount(savedCustomer.getCustomerId());
        final var savedAccount = accountsRepository.save(toSaveAccount);

        return savedAccount.getCustomerId();

    }

    @Override
    public CustomerDto fetchAccountByMobileNumber(String mobileNumber) {

        CustomerEntity customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        AccountsEntity account = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(account, new AccountsDto());

        customerDto.setAccountsDto(accountsDto);

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;

        final var accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {

            AccountsEntity accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );

            AccountsMapper.mapToAccounts(accountsDto, accounts);

            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            CustomerEntity customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);

            isUpdated = true;
        }

        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {

        final var customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        final var customerId = customer.getCustomerId();

        accountsRepository.deleteByCustomerId(customerId);
        customerRepository.deleteById(customerId);

        return true;
    }


    private AccountsEntity createNewAccount(Long id) {
        AccountsEntity newAccount = new AccountsEntity();

        Long accountNumber = new Random().nextLong(1000);

        newAccount.setCustomerId(id);
        newAccount.setAccountNumber(accountNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);

//        newAccount.setCreatedAt(LocalDateTime.now());
//        newAccount.setCreatedBy("system");

        return newAccount;
    }

}
