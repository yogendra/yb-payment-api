package com.yugabyte.samples.paymentapi.persistance;

import com.yugabyte.samples.paymentapi.persistance.model.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {


//  @Query("SELECT a from Account  a where a.customer.id = ?1 and a.id = ?2")
  Optional<Account> findByCustomer_IdAndId(Long customerId, Long id);


}
