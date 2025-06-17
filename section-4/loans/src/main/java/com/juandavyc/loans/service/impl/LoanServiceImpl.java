package com.juandavyc.loans.service.impl;

import com.juandavyc.loans.constants.LoanConstants;
import com.juandavyc.loans.dto.LoanDto;
import com.juandavyc.loans.entity.Loan;
import com.juandavyc.loans.exception.LoanAlreadyExistsException;
import com.juandavyc.loans.exception.ResourceNotFoundException;
import com.juandavyc.loans.mapper.LoanMapper;
import com.juandavyc.loans.respository.LoanRepository;
import com.juandavyc.loans.service.ILoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements ILoanService {

    private final LoanRepository loanRepository;

    @Override
    public Long createLoan(String mobileNumber) {

        boolean exists = loanRepository.existsByMobileNumber(mobileNumber);
        if(exists){
            throw new LoanAlreadyExistsException("Loan with Mobile Number: '" + mobileNumber + "' already exists");
        }
        long loanNumber = new Random().nextLong(1000);

        Loan newLoan = new Loan();
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanNumber(Long.toString(loanNumber));
        newLoan.setLoanType(LoanConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);

        return loanRepository.save(newLoan).getLoanId();

    }

    @Override
    public LoanDto fetchLoanByMobileNumber(String mobileNumber) {
        Loan loanEntity = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan","mobileNumber",mobileNumber)
        );
        LoanDto loanDto = LoanMapper.toDto(loanEntity);

        return loanDto;
    }

    @Override
    public LoanDto fetchLoanById(Long loanId) {
        Loan loanEntity = loanRepository.findById(loanId).orElseThrow(
                () -> new ResourceNotFoundException("Loan","loanId",loanId.toString())
        );
        LoanDto loanDto = LoanMapper.toDto(loanEntity);
        return loanDto;
    }

    @Override
    public boolean updateLoan(LoanDto loanDto) {

        Loan loan = loanRepository.findByMobileNumber(loanDto.getMobileNumber())
                .orElseThrow(()-> new ResourceNotFoundException("Loan","mobileNumber",loanDto.getMobileNumber()));

        Loan loanToUpdate = LoanMapper.toEntity(loanDto);

        loanRepository.save(loanToUpdate);

        return true;
    }
}
