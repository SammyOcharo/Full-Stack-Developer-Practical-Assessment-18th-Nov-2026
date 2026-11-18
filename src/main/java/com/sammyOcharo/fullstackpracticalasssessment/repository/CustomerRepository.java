package com.sammyOcharo.fullstackpracticalasssessment.repository;

import com.sammyOcharo.fullstackpracticalasssessment.model.CustomerEntity;
import jakarta.validation.constraints.Email;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByEmail(@Email String email);

    boolean existsByPhoneNumber(@NonNull String s);

    boolean existsByAccountNumber(@NonNull String accountNumber);

    Optional<CustomerEntity> findByAccountNumber(@NonNull String accountNumber);


}
