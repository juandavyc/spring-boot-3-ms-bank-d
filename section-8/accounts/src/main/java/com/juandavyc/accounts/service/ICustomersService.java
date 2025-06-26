package com.juandavyc.accounts.service;

import com.juandavyc.accounts.dto.CustomerDetailsDto;

public interface ICustomersService {

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);

}
