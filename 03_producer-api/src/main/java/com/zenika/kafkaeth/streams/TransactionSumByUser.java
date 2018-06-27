package com.zenika.kafkaeth.streams;

import com.zenika.kafkaeth.domain.Transaction;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Serialized;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class TransactionSumByUser {
    private static final String TOPIC_TRANSACTIONS_INPUT = "transactions";
    private static final String TOPIC_OUTPUT = "value_by_user";
    public static final String SCHEMA_REGISTRY_URL = "http://localhost:8081";

    public static void main(String[] args){

        final Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "value_by_user-streams");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        streamsConfiguration.put("schema.registry.url", SCHEMA_REGISTRY_URL);
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Double().getClass().getName());

        final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url",
                SCHEMA_REGISTRY_URL);

        final Serde<Transaction> valueSpecificAvroSerde = new SpecificAvroSerde<>();
        valueSpecificAvroSerde.configure(serdeConfig, false);

        // In the subsequent lines we define the processing topology of the Streams application.
        final StreamsBuilder builder = new StreamsBuilder();

        final KStream<String, Transaction> inputTx = builder.stream(TOPIC_TRANSACTIONS_INPUT, Consumed.with(Serdes.String(), valueSpecificAvroSerde));

        final KTable<String, Double> wordCounts = inputTx
                .map((key, transaction) -> new KeyValue<>(transaction.getFromAddress(), transaction))
                .mapValues(Transaction::getValue)
                .filterNot((key, value) -> value == null)
                .groupByKey(Serialized.with(Serdes.String(), Serdes.Double()))
                .reduce(Double::sum);

        wordCounts.toStream().to(TOPIC_OUTPUT, Produced.with(Serdes.String(), Serdes.Double()));

        final KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);

        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        System.out.println(TransactionSumByUser.class.getName() + " started");
    }
}