package com.zenika.kafkaeth.domain;

import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

public class TransactionAdapter {
    public static com.zenika.kafkaeth.domain.Transaction toTransaction(Transaction tx) {
        return new com.zenika.kafkaeth.domain.Transaction(
                tx.getNonce(),
                tx.getHash(),
                tx.getBlockHash(),
                tx.getBlockNumber(),
                tx.getTransactionIndex(),
                tx.getFrom(),
                tx.getTo(),
                Convert.fromWei(new BigDecimal(tx.getValue()), Convert.Unit.ETHER),
                tx.getGasPrice(),
                tx.getGas()
        );
    }
}
