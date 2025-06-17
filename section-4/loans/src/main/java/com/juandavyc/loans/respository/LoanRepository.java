package com.juandavyc.loans.respository;

import com.juandavyc.loans.dto.LoanDto;
import com.juandavyc.loans.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {


    boolean existsByMobileNumber(String mobileNumber);

    Optional<Loan> findByMobileNumber(String mobileNumber);



}
