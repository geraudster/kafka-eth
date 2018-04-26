package com.zenika.kafkaeth;

import com.zenika.kafkaeth.domain.Transaction;
import com.zenika.kafkaeth.interfaces.BlockchainConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 *
 */
public class BlockchainProducerV2 {
    public static void main(String[] args) {
        BlockchainConsumer blockchainConsumer = new BlockchainConsumer(args[0]);
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "localhost:9092");

        kafkaProps.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer",
                "io.confluent.kafka.serializers.KafkaAvroSerializer");

        kafkaProps.put("schema.registry.url", "http://localhost:8081");


        // TP: initialize a KafkaProducer based on Transaction type
        KafkaProducer<String, Transaction> producer = new KafkaProducer<>(kafkaProps);

        // TP: implement sendToKafka method
        //  * Records are sent to a `transactions` topic
        //  * Record ID should be the transaction hash
        //  * Record value is the Transaction *object*
        //
        // Monitor topic with : bin/kafka-avro-console-consumer --bootstrap-server localhost:9092 --topic transactions --from-beginning
        blockchainConsumer.read(tx -> sendToKafka(producer, tx));
    }

    // TP: implement
    private static void sendToKafka(KafkaProducer<String, Transaction> producer, Transaction tx) {
        System.out.println("Sending to kafka: " + tx.getBlockNumber() + " " +
                tx.getValue() + " " +
                tx.getFromAddress() + " " +
                tx.getGasPrice() + " " +
                tx.getToAddress() + " " + tx.getNonce());

        ProducerRecord<String, Transaction> record =
                new ProducerRecord<>("transactions",
                        tx.getHash(),
                        tx);
        try {
            producer.send(record).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
