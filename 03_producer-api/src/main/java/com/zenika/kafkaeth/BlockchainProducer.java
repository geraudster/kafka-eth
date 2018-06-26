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


        // TP: initialize a KafkaProducer based on Transaction type
        final KafkaProducer<?, ?> producer = null;

        blockchainConsumer.read(tx -> sendToKafka(producer, tx));
    }

    // TP: implement sendToKafka method
    //  * Records are sent to a `transactions` topic
    //  * Record ID should be the transaction hash
    //  * Record value is the Transaction *object*
    //
    // Monitor topic with : bin/kafka-avro-console-consumer --bootstrap-server localhost:9092 --topic transactions --from-beginning
    private static void sendToKafka(KafkaProducer<?, ?> producer, Transaction tx) {
        System.out.println("Sending to kafka: " + tx.toString());

        final ProducerRecord<?, ?> record = null;
    }
}
