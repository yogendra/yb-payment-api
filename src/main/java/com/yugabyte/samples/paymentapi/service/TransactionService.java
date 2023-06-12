package com.yugabyte.samples.paymentapi.service;

import static com.yugabyte.samples.paymentapi.service.PaymentTransactionException.accountNotFound;
import static com.yugabyte.samples.paymentapi.service.PaymentTransactionException.insufficientFund;
import static com.yugabyte.samples.paymentapi.service.PaymentTransactionException.transactionFailure;

import com.yugabyte.samples.paymentapi.persistance.AccountRepository;
import com.yugabyte.samples.paymentapi.persistance.CustomerRepository;
import com.yugabyte.samples.paymentapi.persistance.TransactionRepository;
import com.yugabyte.samples.paymentapi.persistance.model.Account;
import com.yugabyte.samples.paymentapi.persistance.model.Transaction;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionService {
  private CustomerRepository customers;
  private AccountRepository accounts;
  private TransactionRepository transactions;

  public TransactionService(CustomerRepository customers, AccountRepository accounts,
    TransactionRepository transactions) {
    this.customers = customers;
    this.accounts = accounts;
    this.transactions = transactions;
  }

  @Transactional
  public TransactionResponse processTransaction(TransactionRequest request)
    throws PaymentTransactionException {
    String correlationId = UUID.randomUUID()
      .toString();

    Long customerId = request.getCustomerId();
    Long debitAccountNumber = request.getDebitAccount();
    Long creditAccountNumber = request.getCreditAccount();
    Account creditAccount = getAccount(creditAccountNumber, correlationId);
    Double amount = request.getAmount();

    Account debitAccount = getCustomerAccount(customerId, debitAccountNumber, correlationId);
    if (debitAccount.getBalance() - amount < 0) {
      throw insufficientFund(correlationId);
    }

    Transaction transaction = Transaction.builder()
      .id(UUID.randomUUID())
      .debitAmount(amount)
      .debitCurrency(request.getCurrency())
      .debitAccount(debitAccount)
      .creditAccount(creditAccount)
      .creditAmount(amount)
      .creditCurrency(request.getCurrency())
      .build();

    debitAccount.setBalance(debitAccount.getBalance() - amount);

    creditAccount.setBalance(creditAccount.getBalance() + amount);
    try {
      transactions.save(transaction);
      accounts.saveAll(Arrays.asList(debitAccount, creditAccount));
      var tres = TransactionResponse.builder()
        .transactionId(transaction.getId()
          .toString())
        .status("SUCCESS")
        .build();
      log.info("{}: successful", correlationId);
      return tres;
    } catch (Exception ex) {
      PaymentTransactionException pte = transactionFailure(correlationId, ex);
      logException(pte);
      throw pte;
    }
  }

  private Account getAccount(Long accountId, String correlationId)
    throws PaymentTransactionException {
    Optional<Account> optionalAccount = accounts.findById(accountId);
    if (optionalAccount.isEmpty()) {
      var pte = accountNotFound(correlationId);
      logException(pte);
      throw pte;
    }
    return optionalAccount.get();
  }


  private Account getCustomerAccount(Long customerId, Long accountId, String correlationId)
    throws PaymentTransactionException {
    Optional<Account> optionalAccount = accounts.findByCustomer_IdAndId(customerId, accountId);
    if (optionalAccount.isEmpty()) {
      var pte = accountNotFound(correlationId);
      logException(pte);
      throw pte;
    }
    return optionalAccount.get();
  }
  private void logException(PaymentTransactionException pte) {
    log.error("TRANSACTION FAILED {} {} {}", pte.getCorrelationId(), pte.getCode(), pte.getDescription());
  }
}
