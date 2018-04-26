-- Create stream

CREATE STREAM transactions () \
WITH (KAFKA_TOPIC='transactions', VALUE_FORMAT='avro');

-- Queries

SELECT fromAddress, toAddress, value \
FROM transactions \
LIMIT 3;

SELECT fromAddress, toAddress, value \
FROM transactions \
WHERE value > 1 \
LIMIT 3;

-- Persistent queries

CREATE STREAM big_transactions AS \
SELECT fromAddress, toAddress, value \
FROM transactions \
WHERE value > 1;

-- Stream to table

CREATE TABLE big_transactions_stats AS \
SELECT fromAddress, sum(value), count(value) \
FROM transactions WINDOW TUMBLING (size 1 minute) \
GROUP BY fromAddress;
