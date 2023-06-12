package com.yugabyte.samples.paymentapi.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
  @JsonInclude(Include.NON_ABSENT)
  private String failureReason = null;
}
