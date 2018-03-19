package com.zenika.kafkaeth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import rx.Subscription;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class KafkaEthApplication {

    public final static int COUNT = 10;
    @Autowired
    private Web3j web3j;

    public static void main(String[] args) {
        SpringApplication.run(KafkaEthApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            CountDownLatch countDownLatch = new CountDownLatch(COUNT);


            System.out.println("Waiting for " + COUNT + " transactions...");

            Subscription subscription = web3j.blockObservable(true)
                    .take(COUNT)
                    .subscribe(ethBlock -> {
                        EthBlock.Block block = ethBlock.getBlock();
                        LocalDateTime timestamp = Instant.ofEpochSecond(
                                block.getTimestamp().longValueExact()).atZone(ZoneId.of("UTC")).toLocalDateTime();

                        int transactionCount = block.getTransactions().size();
                        String hash = block.getHash();
                        String parentHash = block.getParentHash();

                        System.out.println(
                                timestamp + " " +
                                        "Tx count: " + transactionCount + ", " +
                                        "Hash: " + hash + ", " +
                                        "Parent hash: " + parentHash
                        );

                        countDownLatch.countDown();
                    }, Throwable::printStackTrace);

            subscription.unsubscribe();
        };
    }

}
