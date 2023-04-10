package com.yugabyte.samples.paymentapi.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
  private Long customerId;
  private Long debitAccount;
  private Long creditAccount;

  private String currency;
  private Double amount;

}
