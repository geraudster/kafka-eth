package com.zenika.kafkaeth.interfaces;

import com.zenika.kafkaeth.domain.Transaction;
import com.zenika.kafkaeth.domain.TransactionAdapter;
import org.web3j.ens.EnsResolver;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.util.function.Consumer;

public class BlockchainConsumer {
    private Web3j web3j;

    public BlockchainConsumer(String ethHttpServiceUrl) {
        web3j = Web3j.build(new HttpService(ethHttpServiceUrl));
    }
    public void read(Consumer<Transaction> kafkaProducerOp) {
        EnsResolver ensResolver = new EnsResolver(web3j);
        web3j.transactionObservable()
                .doOnError(System.err::println)
                .subscribe(tx -> {
                    String fromName = ensResolver.resolve(tx.getFrom());
                    String toName = ensResolver.resolve(tx.getTo());
                    kafkaProducerOp.accept(TransactionAdapter.toTransaction(tx, fromName, toName));
                });
    }
}
