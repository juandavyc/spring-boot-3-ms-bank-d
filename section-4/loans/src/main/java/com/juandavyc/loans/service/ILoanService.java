package com.juandavyc.loans.service;


import com.juandavyc.loans.dto.LoanDto;

import java.util.Optional;

public interface ILoanService {

    Long createLoan(String mobileNumber);

    LoanDto fetchLoanByMobileNumber(String mobileNumber);

    LoanDto fetchLoanById(Long loanId);

    boolean updateLoan(LoanDto loanDto);
}
