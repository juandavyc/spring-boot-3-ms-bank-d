package com.juandavyc.accounts.service;

import com.juandavyc.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * @param customerDto - customerDto Object
     * @return Long - id
     */
    Long createAccount(CustomerDto customerDto);

    CustomerDto fetchAccountByMobileNumber(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);

}
