package com.yugabyte.samples.paymentapi.api;

import static com.yugabyte.samples.paymentapi.api.PaymentResponse.PaymentResponseStatus.FAILED;
import static com.yugabyte.samples.paymentapi.api.PaymentResponse.PaymentResponseStatus.SUCCESS;
import static java.lang.String.format;

import com.yugabyte.samples.paymentapi.service.PaymentTransactionException;
import com.yugabyte.samples.paymentapi.service.TransactionRequest;
import com.yugabyte.samples.paymentapi.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentApi {

  private TransactionService transactionService;

  public PaymentApi(TransactionService transactionService) {
    this.transactionService = transactionService;
  }


  @PostMapping
  public ResponseEntity<PaymentResponse> pay(@RequestBody PaymentRequest request) {
    TransactionRequest treq = TransactionRequest.builder()
      .amount(request.getAmount())
      .creditAccount(request.getCreditAccount())
      .currency(request.getCurrency())
      .customerId(request.getCustomerId())
      .debitAccount(request.getDebitAccount())
      .build();

    var responseBuilder = PaymentResponse.builder();

    try {
      var tres = transactionService.processTransaction(treq);
      responseBuilder
        .status(SUCCESS)
        .description("Done")
        .transactionId(tres.getTransactionId())
        .build();
      return ResponseEntity.ok(responseBuilder.build());
    } catch (PaymentTransactionException e) {
      var description = format("""
        Correlation ID: %1$s
        Error Code: %2$s
        Description: %3$s
        """, e.getCorrelationId(), e.getCode(), e.getDescription());
      responseBuilder
        .status(FAILED)
        .description(description)
        .transactionId("NONE");
      return ResponseEntity.badRequest().body(responseBuilder.build());
    }
  }
}
