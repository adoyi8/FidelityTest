package com.sundroid.fidelity;


import com.sundroid.fidelity.models.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {

}
