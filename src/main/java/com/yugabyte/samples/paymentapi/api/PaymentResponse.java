package com.yugabyte.samples.paymentapi.api;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse implements Serializable {
  private String transactionId;
  private String description;
  private PaymentResponseStatus status;

  public static enum PaymentResponseStatus{
    SUCCESS, PENDING, FAILED;
  }
}
