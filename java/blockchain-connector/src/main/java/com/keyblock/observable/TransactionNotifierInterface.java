package com.keyblock.observable;

public interface TransactionNotifierInterface {
    public void subscribe(String transactionHash, TransactionListenerInterface listener);
}
