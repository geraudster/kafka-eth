package com.zenika.kafkaeth;

import com.zenika.kafkaeth.domain.Transaction;
import com.zenika.kafkaeth.interfaces.BlockchainConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 *
 */
public class BlockchainProducer {

    private static final String TOPIC_TRANSACTIONS = "transactions";

    public static void main(String[] args) {
        String ethHttpUrlService = args[0];
        BlockchainConsumer blockchainConsumer = new BlockchainConsumer(ethHttpUrlService);
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "localhost:9092");

        kafkaProps.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer",
                "io.confluent.kafka.serializers.KafkaAvroSerializer");

        kafkaProps.put("schema.registry.url", "http://localhost:8081");


        final KafkaProducer<String, Transaction> producer = new KafkaProducer<>(kafkaProps);

        blockchainConsumer.read(tx -> sendToKafka(producer, tx));
    }

    private static void sendToKafka(KafkaProducer<String, Transaction> producer, Transaction tx) {
        System.out.println("Sending to kafka: " + tx.toString());

        final ProducerRecord<String, Transaction> record = new ProducerRecord<>(TOPIC_TRANSACTIONS, tx.getHash(), tx);

        try {
            producer.send(record).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
