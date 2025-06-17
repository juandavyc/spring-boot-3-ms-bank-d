package com.juandavyc.accounts.repository;

import com.juandavyc.accounts.entity.AccountsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<AccountsEntity,Long> {

    Optional<AccountsEntity> findByCustomerId(Long customerId);

    @Transactional
    @Modifying
    void deleteByCustomerId(Long customerId);
}
