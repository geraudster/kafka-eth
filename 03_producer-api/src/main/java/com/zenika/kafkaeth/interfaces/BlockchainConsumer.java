package com.zenika.kafkaeth.interfaces;

import com.zenika.kafkaeth.domain.Transaction;
import com.zenika.kafkaeth.domain.TransactionAdapter;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.rx.Web3jRx;

import java.util.function.Consumer;

public class BlockchainConsumer {
    private Web3jRx web3j;

    public BlockchainConsumer(String ethHttpServiceUrl) {
        web3j = Web3j.build(new HttpService(ethHttpServiceUrl));
    }
    public void read(Consumer<Transaction> kafkaProducerOp) {
        web3j.transactionObservable()
                .doOnError(System.err::println)
                .subscribe(tx -> kafkaProducerOp.accept(TransactionAdapter.toTransaction(tx)));
    }
}
