package com.sammyOcharo.fullstackpracticalasssessment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String accountNumber;

    @NotBlank
    @Column(nullable = false, unique = true)
    private Double accountBalance;

    @Column(nullable = false)
    private boolean isActive=true;
}
