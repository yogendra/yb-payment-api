CREATE TABLE IF NOT EXISTS customers
(
  id   int primary key,
  name varchar(100) not null
);

CREATE TABLE IF NOT EXISTS accounts
(
  id          int primary key,
  customer_id int        not null,
  balance     float8     not null default 0,
  currency    varchar(3) not null default 'USD'
);

CREATE TABLE IF NOT EXISTS transactions
(
  id              uuid primary key,
  debit_account   int        not null,
  debit_currency  varchar(3) not null default 'USD',
  debit_amount    float8     not null default 0,
  credit_account  int        not null,
  credit_currency varchar(3) not null default 'USD',
  credit_amount   float8     not null default 0
);

ALTER TABLE accounts
  ADD CONSTRAINT fk_accounts_customer
    FOREIGN KEY (customer_id) REFERENCES customers;

CREATE INDEX idx_customer_account
  on accounts (customer_id, id);

ALTER TABLE transactions
  ADD CONSTRAINT fk_transactions_debit_account
    FOREIGN KEY (debit_account) REFERENCES accounts;

ALTER TABLE transactions
  ADD CONSTRAINT fk_transactions_credit_account
    FOREIGN KEY (credit_account) REFERENCES accounts;



CREATE TABLE IF NOT EXISTS testdata
(
  id             int primary key,
  debit_account  int,
  credit_account int,
  customer_id    int,
  amount         float8,
  currency       varchar(3)
);
