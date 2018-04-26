This project stores data in blockchain.

# Goal

Your objective is to send blockchain transactions into a Kafka topic.
Please complete `BlockchainProducer` class.

# Run

```
./mvnw compile exec:java -Dexec.args=[geth client url]
```

Check message in `transactions` topic with the command:

```
bin/kafka-avro-console-consumer --bootstrap-server localhost:9092 --topic transactions --from-beginning
```
