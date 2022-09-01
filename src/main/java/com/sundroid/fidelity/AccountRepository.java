package com.sundroid.fidelity;


import com.sundroid.fidelity.models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountModel, Long> {
    Optional<AccountModel> findByAccountNumber(String s);
    Optional<AccountModel> findByEmail(String s);

}
