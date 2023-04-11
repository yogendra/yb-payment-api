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

2. Docker

    ```bash
   docker run --name payment-api -p 8443:8443 ghcr.io/yogendra/yb-payment-api:latest
    ```
## Customize application

This is a spring boot application, so you can change the behavior by setting environment variables, JVM Parameteres (`-D`) or arguments. Following is list of settings to change application behaviour.

| Spring Config  / JVM Arg  / App Arg | Env variable            | Description                                      | Example / Default                                       |
|-------------------------------------|-------------------------|--------------------------------------------------|---------------------------------------------------------|
| yb.username                         | YB_USERNAME             | DB Username                                      | --yb.ysername=yugabyte                                  |
 | yb.password                         | YB_PASSWORD             | DB Password                                      | --yb.password=''                                        |
| yb.host                             | YB_HOST                 | DB Host                                          | --yb.host=127.0.0.1                                     |
| yb.port                             | YB_PORT                 | DB Port                                          | --yb.port=5433                                          |
| yb.additional-endpoints             | YB_ADDITIONAL_ENDPOINTS | DB Additinal tserver addresses (comma-separated) | --yb.additional-endpoints=127.0.0.2:5433,127.0.0.3:5433 |
| yb.topology-keys                    | YB_TOPOLOGY_KEYS        | DB Topology keys for local region/zone/rack      | --yb.topology-keys=cloud1.datacenter1.rack1             |
| yb.max-pool-size                    | YB_MAX_POOL_SIZE        | DB Connection pool maximum size                  | --yb.max-pool-size=25                                   |
| yb.max-lifetime                     | YB_MAX_LIFETIME         | DB Connection maximum lifetimes                  | --yb.max-lifetime=30000                                 |
| spring.jpa.show-sql                 | SPRING_JPA_SHOW_SQL     | Show SQL statements                              | --spring.jpa.show-sql=true                              |

Other Spring boot configuration (loggin, monitoring, etc.) can also be set. Read [spring boot documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html)

## Examples App Startup Commands

1. Local Java

    ```bash
    java -jar target/payment-api-0.0.1-SNAPSHOT.jar \
      --yb.host=127.0.0.1 \
      --yb.port=5433 \
      --yb.username=yugbayte \
      --yb.password=P@ssw0rd \
      --yb.additional-endpoints=127.0.0.2:5433,127.0.0.3:5433 \
      --yb.topology-keys=cloud1.datacenter1.rack1
    ```
2. Docker

    ```bash
   docker run --rm -it --name=payment-api --hostname=payment-api ghcr.io/yogendra/yb-payment-api \
    --yb.host=127.0.0.1 \
    --yb.port=5433 \
    --yb.username=yugbayte \
    --yb.password=P@ssw0rd \
    --yb.additional-endpoints=127.0.0.2:5433,127.0.0.3:5433 \
    --yb.topology-keys=cloud1.datacenter1.rack1
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

