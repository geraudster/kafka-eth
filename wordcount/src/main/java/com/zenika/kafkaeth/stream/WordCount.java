package com.zenika.kafkaeth.stream;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Pattern;

public class WordCount {

    private static final String TOPIC_WORDCOUNT_INPUT = "wordcount-input";
    private static final String TOPIC_WORDCOUNT_OUTPUT = "streams-wordcount-output";

    public static void main(String[] args){

        final Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-lambda-example");
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        // In the subsequent lines we define the processing topology of the Streams application.
        final StreamsBuilder builder = new StreamsBuilder();


        //*****************
        //TODO : A IMPLEMENTER EN SUIVANT LES ETAPES
        //*****************

        //1- Streamer les lignes du topic input
        //2- Faire un lower case + ressortir les mots de la ligne (split espace)
        //3- Regrouper par mot
        //4- compter le nombre d'occurence
        //5- Envoyer le résultat dans le topic output

//        streams.start();
//
//        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}