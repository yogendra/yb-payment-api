package com.yugabyte.samples.paymentapi.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransactionException extends Exception{
  private String code;
  private String correlationId;
  private String description;

  public static PaymentTransactionException insufficientFund(String correlationId){
    return new PaymentTransactionException("INSUFFICIENT_FUNDS", correlationId, "Insufficient Funds");
  }
  public static PaymentTransactionException customerNotFound(String correlationId){
    return new PaymentTransactionException("CUSTOMER_NOT_FOUND", correlationId, "Customer Not Found in Records");
  }
  public static PaymentTransactionException accountNotFound(String correlationId){
    return new PaymentTransactionException("ACCOUNT_NOT_FOUND", correlationId, "Account Not Found in Records");
  }
  public static PaymentTransactionException accountNotOwnedByCustomer(String correlationId){
    return new PaymentTransactionException("ACCOUNT_NOT_FOUND", correlationId, "Account Not Owner by Customer");
  }

}
