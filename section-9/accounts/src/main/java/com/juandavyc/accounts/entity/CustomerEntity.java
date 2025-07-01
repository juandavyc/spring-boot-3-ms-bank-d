package com.juandavyc.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer")

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class CustomerEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String name;

    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

}
