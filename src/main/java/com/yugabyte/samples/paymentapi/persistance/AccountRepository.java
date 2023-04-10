package com.yugabyte.samples.paymentapi.persistance;

import com.yugabyte.samples.paymentapi.persistance.model.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

  Optional<Account> findByCustomer_IdAndId(Long customerId, Long id);


}
