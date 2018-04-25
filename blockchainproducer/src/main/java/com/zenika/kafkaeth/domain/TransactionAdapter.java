package com.zenika.kafkaeth.domain;

import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

public class TransactionAdapter {
    public static com.zenika.kafkaeth.domain.Transaction toTransaction(Transaction tx) {
        return new com.zenika.kafkaeth.domain.Transaction(
                tx.getNonce().longValue(),
                tx.getHash(),
                tx.getBlockHash(),
                tx.getBlockNumber().longValue(),
                tx.getTransactionIndex().longValue(),
                tx.getFrom(),
                tx.getTo(),
                Convert.fromWei(new BigDecimal(tx.getValue()), Convert.Unit.ETHER).doubleValue(),
                tx.getGasPrice().longValue(),
                tx.getGas().longValue()
        );
    }
}
