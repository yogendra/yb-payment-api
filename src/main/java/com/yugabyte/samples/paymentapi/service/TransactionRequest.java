package com.yugabyte.samples.paymentapi.service;

import java.util.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {
  private Long customerId;
  private Long debitAccount;
  private Long creditAccount;

  private String currency;
  private Double amount;


}
