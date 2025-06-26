package com.juandavyc.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accounts")


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class AccountsEntity extends BaseEntity {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long accountNumber;

    private String accountType;

    private String branchAddress;

    private Long customerId;

}
