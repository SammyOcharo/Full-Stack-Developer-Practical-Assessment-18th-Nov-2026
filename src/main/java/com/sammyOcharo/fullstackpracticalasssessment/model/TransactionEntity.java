package com.sammyOcharo.fullstackpracticalasssessment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_transactions")
@Data
public class TransactionEntity {

    @Id
    private UUID transaction_id;
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;
    @NonNull
    private TransactionStatus transactionStatus;

}
