package com.juandavyc.accounts.repository;

import com.juandavyc.accounts.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {

    boolean existsByMobileNumber(String mobileNumber);

    Optional<CustomerEntity> findByMobileNumber(String mobileNumber);

    @Query("SELECT c.customerId FROM CustomerEntity c WHERE c.mobileNumber = :mobileNumber")
    Optional<Long> findCustomerIdByMobileNumber(String mobileNumber);


//    Optional<Long> findIdByMobileNumber(String mobileNumber);

}
