package com.yugabyte.samples.paymentapi.persistance;

import com.yugabyte.samples.paymentapi.persistance.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
