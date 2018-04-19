package com.zenika.kafkaeth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.web3j.protocol.Web3j;
import rx.Subscription;

import java.util.concurrent.TimeUnit;

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

            Subscription subscription = web3j.transactionObservable().doOnError(System.err::println).subscribe(tx -> {
                System.out.println(tx.getS() + " " + tx.getV() + " " + tx.getFrom() + " " + tx.getGasPrice() + " " + tx.getTo());
            });
            TimeUnit.MINUTES.sleep(2);

            subscription.unsubscribe();
        };
    }

}
