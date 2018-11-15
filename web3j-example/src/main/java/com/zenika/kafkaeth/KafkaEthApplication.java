package com.zenika.kafkaeth;


import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.websocket.WebSocketService;
import rx.Subscription;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

public class KafkaEthApplication {

    public static void main(String[] args) throws InterruptedException, ConnectException {
        final String ethHttpServiceUrl = args[0];

        final Web3jService web3jService;
        if (ethHttpServiceUrl.startsWith("ws")) {
            WebSocketService webSocketService= new WebSocketService(ethHttpServiceUrl, true);
            webSocketService.connect();
            web3jService = webSocketService;
        } else {
            web3jService = new HttpService(ethHttpServiceUrl);
        }
        Web3j web3j = Web3j.build(web3jService);
//        Web3j web3j = Web3j.build(new HttpService(ethHttpServiceUrl));

        Subscription subscription = web3j.transactionObservable()
                .doOnError(System.err::println)
                .map(tx ->
                        {
                            System.out.println(
                                    tx.getS() + " "
                                            + tx.getV() + " "
                                            + tx.getFrom() + " "
                                            + tx.getGasPrice() + " "
                                            + tx.getTo());
                            return tx;
                        }
                )
                .buffer(10, TimeUnit.SECONDS)
                .doOnNext(txs -> System.out.println("*** " + txs.size() + " transactions"))
                .subscribe();
        TimeUnit.MINUTES.sleep(2);

        subscription.unsubscribe();
    }
}
