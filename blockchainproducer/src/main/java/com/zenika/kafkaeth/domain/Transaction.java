package com.zenika.kafkaeth.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class Transaction {
    private BigInteger nonce;
    private String hash;
    private String blockHash;
    private BigInteger blockNumber;
    private BigInteger transactionIndex;
    private String from;
    private String to;
    private BigDecimal value;
    private BigInteger gasPrice;
    private BigInteger gas;

    Transaction(BigInteger nonce, String hash, String blockHash, BigInteger blockNumber, BigInteger transactionIndex, String from, String to, BigDecimal value, BigInteger gasPrice, BigInteger gas) {
        this.nonce = nonce;
        this.hash = hash;
        this.blockHash = blockHash;
        this.blockNumber = blockNumber;
        this.transactionIndex = transactionIndex;
        this.from = from;
        this.to = to;
        this.value = value;
        this.gasPrice = gasPrice;
        this.gas = gas;
    }

    public BigInteger getNonce() {
        return nonce;
    }

    public String getHash() {
        return hash;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public BigInteger getBlockNumber() {
        return blockNumber;
    }

    public BigInteger getTransactionIndex() {
        return transactionIndex;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public BigDecimal getValue() {
        return value;
    }

    public BigInteger getGasPrice() {
        return gasPrice;
    }

    public BigInteger getGas() {
        return gas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(nonce, that.nonce) &&
                Objects.equals(hash, that.hash) &&
                Objects.equals(blockHash, that.blockHash) &&
                Objects.equals(blockNumber, that.blockNumber) &&
                Objects.equals(transactionIndex, that.transactionIndex) &&
                Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(value, that.value) &&
                Objects.equals(gasPrice, that.gasPrice) &&
                Objects.equals(gas, that.gas);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nonce, hash, blockHash, blockNumber, transactionIndex, from, to, value, gasPrice, gas);
    }
}
