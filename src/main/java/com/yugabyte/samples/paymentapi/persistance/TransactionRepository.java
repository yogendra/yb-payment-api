package com.yugabyte.samples.paymentapi.persistance;

import com.yugabyte.samples.paymentapi.persistance.model.Transaction;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface  TransactionRepository extends JpaRepository<Transaction, UUID> {

}
