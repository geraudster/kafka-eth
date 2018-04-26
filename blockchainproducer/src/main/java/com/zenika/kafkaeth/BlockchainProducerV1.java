package com.zenika.kafkaeth;

import com.zenika.kafkaeth.domain.Transaction;
import com.zenika.kafkaeth.interfaces.BlockchainConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 *
 */
public class BlockchainProducerV1 {
    public static void main(String[] args) {
        String ethHttpUrlService = args[0];
        BlockchainConsumer blockchainConsumer = new BlockchainConsumer(ethHttpUrlService);
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "localhost:9092");

        kafkaProps.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        final KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaProps);

        //  * Records are sent to a `transactions` topic
        //  * Record ID should be the transaction hash
        //  * Record value is the transaction value field
        //
        // Monitor topic with : bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic transactions --from-beginning
        blockchainConsumer.read(tx -> sendToKafka(producer, tx));
    }

    private static void sendToKafka(KafkaProducer<String, String> producer, Transaction tx) {
        System.out.println("Sending to kafka: " + tx.getBlockNumber() + " " +
                tx.getValue() + " " +
                tx.getFromAddress() + " " +
                tx.getGasPrice() + " " +
                tx.getToAddress() + " " + tx.getNonce());

        final ProducerRecord<String, String> record =
                new ProducerRecord<>("transactions",
                        tx.getHash(),
                        tx.getValue().toString());
        try {
            producer.send(record).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
