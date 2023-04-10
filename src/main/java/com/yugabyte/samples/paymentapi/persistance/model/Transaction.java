package com.yugabyte.samples.paymentapi.persistance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transactions")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToOne
  @PrimaryKeyJoinColumn(name = "debit_account")
  private Account debitAccount;

  @OneToOne
  @PrimaryKeyJoinColumn(name = "credit_account")
  private Account creditAccount;
  private String debitCurrency;
  private Double debitAmount;

  private String creditCurrency;
  private Double creditAmount;


}
