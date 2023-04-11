# Payment API - Sample App


## Build App

```bash
./mvnw clean install
```

## Run

1. Local Java

    ```bash
    java -jar target/payment-api-0.0.1-SNAPSHOT.jar
    ```

1. Docker

    ```bash
   docker run --name payment-api -p 8443:8443 ghcr.io/yogendra/yb-payment-api:latest
    ```
## Customize application

This is a spring boot application, so you can change the behavior by setting environment variables, JVM Parameteres (`-D`) or arguments. Following is list of settings to change application behaviour.

| Spring config | Env variable | JVM Arg | Parameter | Description | Example |
|- |- |- |- |- |
| | | | | |

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


## Payment API

```bash
curl -X POST \
  -k \
  -H "Accept: application/json" \
  -H "Content-Type: application/json" \
  https://localhost:8443/api/v1/payments \
  -d '{
  "customerId": 1,
  "debitAccount": 10000001,
  "creditAccount": 10000002,
  "amount" : 44.0,
  "currency" : "USD"
  }'

```

