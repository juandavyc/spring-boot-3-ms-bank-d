package com.juandavyc.loans.mapper;

import com.juandavyc.loans.dto.LoanDto;
import com.juandavyc.loans.entity.Loan;

public class LoanMapper {

    public static Loan toEntity(LoanDto loanDto) {
        Loan loan = new Loan();

        loan.setMobileNumber(loanDto.getMobileNumber());
        loan.setLoanNumber(loanDto.getLoanNumber());
        loan.setLoanType(loanDto.getLoanType());
        loan.setTotalLoan(loanDto.getTotalLoan());
        loan.setAmountPaid(loanDto.getAmountPaid());
        loan.setOutstandingAmount(loanDto.getOutstandingAmount());

        return loan;
    }

    public static LoanDto toDto(Loan loan) {
        LoanDto loanDto = new LoanDto();

        loanDto.setMobileNumber(loan.getMobileNumber());
        loanDto.setLoanNumber(loan.getLoanNumber());
        loanDto.setLoanType(loan.getLoanType());
        loanDto.setTotalLoan(loan.getTotalLoan());
        loanDto.setAmountPaid(loan.getAmountPaid());
        loanDto.setOutstandingAmount(loan.getOutstandingAmount());
        return loanDto;
    }




}
