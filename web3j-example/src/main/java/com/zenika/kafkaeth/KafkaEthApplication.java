package com.zenika.kafkaeth;


import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import rx.Subscription;

import java.util.concurrent.TimeUnit;

public class KafkaEthApplication {

    public static void main(String[] args) throws InterruptedException {
        String ethHttpServiceUrl = args[0];
        Web3j web3j = Web3j.build(new HttpService(ethHttpServiceUrl));

        Subscription subscription = web3j.transactionObservable()
                .doOnError(System.err::println)
                .subscribe(tx ->
                        System.out.println(
                                tx.getS() + " "
                                        + tx.getV() + " "
                                        + tx.getFrom() + " "
                                        + tx.getGasPrice() + " "
                                        + tx.getTo())
                );
        TimeUnit.MINUTES.sleep(2);

        subscription.unsubscribe();
    }
}
