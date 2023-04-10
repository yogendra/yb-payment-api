package com.yugabyte.samples.paymentapi.api;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest implements Serializable {
  private Long customerId;
  private Long debitAccount;
  private Long creditAccount;
  private String currency;
  private Double amount;

}
