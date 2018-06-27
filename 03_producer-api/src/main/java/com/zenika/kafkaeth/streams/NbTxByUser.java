package com.zenika.kafkaeth.streams;

import com.zenika.kafkaeth.domain.Transaction;
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

public class NbTxByUser {

    private static final String TOPIC_TRANSACTIONS_INPUT = "transactions";
    private static final String TOPIC_OUTPUT = "nb_tx_by_user";
    public static final String SCHEMA_REGISTRY_URL = "http://localhost:8081";

    public static void main(String[] args){

        final Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "nb_tx_by_user-streams");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        streamsConfiguration.put("schema.registry.url", SCHEMA_REGISTRY_URL);

        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();
        final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url",
                SCHEMA_REGISTRY_URL);

        final Serde<Transaction> valueSpecificAvroSerde = new SpecificAvroSerde<>();
        valueSpecificAvroSerde.configure(serdeConfig, false);

        // In the subsequent lines we define the processing topology of the Streams application.
        final StreamsBuilder builder = new StreamsBuilder();

        final KStream<String, Transaction> inputTx = builder.stream(TOPIC_TRANSACTIONS_INPUT, Consumed.with(stringSerde, valueSpecificAvroSerde));

        final KTable<String, Long> wordCounts = inputTx
                .map((key, transaction) -> new KeyValue<>(transaction.getFromAddress(), transaction))
                .groupByKey(Serialized.with(stringSerde, valueSpecificAvroSerde))
                .count();

        wordCounts.toStream().to(TOPIC_OUTPUT, Produced.with(stringSerde, longSerde));

        final KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);

        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        System.out.println(NbTxByUser.class.getName() + " started");
    }
}
