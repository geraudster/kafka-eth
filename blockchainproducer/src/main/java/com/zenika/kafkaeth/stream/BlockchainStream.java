package com.zenika.kafkaeth.stream;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;

import com.zenika.kafkaeth.domain.Transaction;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

public class BlockchainStream {

    private static final String TOPIC_INPUT = "transactions";
    private static final String NB_TX_BY_USER_OUTPUT = "nb-tx-by-user-data";
    private static final String VALUE_BY_USER_OUTPUT = "value-by-user-data";

    public static void main(String[] args) {

        Long windowSizeMs = TimeUnit.MINUTES.toMillis(2);
        Long advanceMs = TimeUnit.SECONDS.toMillis(2);
        TimeWindows timeWindows = TimeWindows.of(windowSizeMs).advanceBy(advanceMs);

        KafkaStreams streamsTxByUser = initStreamsTxByUser();
        streamsTxByUser.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streamsTxByUser::close));

        KafkaStreams streamsValueByUser = initStreamsValueByUser();
        streamsValueByUser.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streamsValueByUser::close));
    }

    private static KafkaStreams initStreamsTxByUser(){
        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, Transaction> inputTx = builder.stream(TOPIC_INPUT);

        //TODO Implement
        KTable<String, Long> nbTxByUser = null;



        final Properties streamsConfiguration = initTxByUserStreamProperties();
        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);
        return streams;
    }

    private static Properties initTxByUserStreamProperties() {
        final Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "avro-stream-tx");
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        streamsConfiguration.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

        return streamsConfiguration;
    }

    private static KafkaStreams initStreamsValueByUser(){
        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, Transaction> inputTx = builder.stream(TOPIC_INPUT);

        //TODO implement
        KTable<String, Double> valueByUser = null;

        valueByUser.toStream().to(VALUE_BY_USER_OUTPUT, Produced.with(Serdes.String(), Serdes.Double()));

        final Properties streamsConfiguration = initValueByUserStreamProperties();
        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);
        return streams;
    }

    private static Properties initValueByUserStreamProperties() {
        final Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "avro-stream-value");
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Double().getClass().getName());
        streamsConfiguration.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

        return streamsConfiguration;
    }
}
