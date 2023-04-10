package com.yugabyte.samples.paymentapi.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
  private String transactionId;
  private String status = "SUCCESS";
}
