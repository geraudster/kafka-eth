package com.zenika.kafkaeth;

import com.zenika.kafkaeth.interfaces.BlockchainConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * Monitor topic with : bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic transactions --from-beginning
 */
public class BlockchainProducerV1 {
    public static void main(String[] args) {
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "localhost:9092");

        kafkaProps.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaProps);

        BlockchainConsumer blockchainConsumer = new BlockchainConsumer(args[0]);
        blockchainConsumer.read(tx -> {
            System.out.println("Sending to kafka: " + tx.getBlockNumber() + " " +
                    tx.getValue() + " " +
                    tx.getFrom() + " " +
                    tx.getGasPrice() + " " +
                    tx.getTo() + tx.getNonce());

            ProducerRecord<String, String> record =
                    new ProducerRecord<>("transactions",
                            tx.getBlockNumber().toString(),
                            tx.getValue().toString());
            try {
                producer.send(record).get();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }
}
