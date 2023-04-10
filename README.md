# Payment API - Sample App


## Build App

```bash
./mvnw clean install
```

## Run JMeter

```bash
jmeter -t src/test/payment-api.jmx -Joffset=0 -Jlimit=100
```
## Load Test Data into YugabyteDB

```bash
ysqlsh -c "COPY customers(id, name) FROM '$PWD/src/test/data/customers.csv' WITH (FORMAT 'csv',  HEADER true);"
ysqlsh -c "COPY accounts(balance, currency, customer_id, id) FROM '$PWD/src/test/data/accounts.csv' WITH (FORMAT 'csv', HEADER true);"
ysqlsh -c "COPY testdata(amount, credit_account, currency, customer_id, debit_account,id) FROM '$PWD/src/test/data/testdata.csv' WITH (FORMAT 'csv', HEADER true);"
```

## Regenerate test data
This app uses [synth](https://www.getsynth.com/) to generate the test data. You can regenerate the CSVs by running following commands:

```bash
# remove existing csv data
rm -rf src/test/data

# update lengths in the src/test/synth/customer.json, src/test/synth/accounts.json and src/test/synth/testdata.json

# generate test data
synth generate   src/test/synth --to csv:src/test/data
```


