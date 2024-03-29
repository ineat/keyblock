package com.keyblock.observable;


import com.keyblock.model.TxReceipt;

/**
 * Interface to implement to be able to subscribe and be notified when a transaction is validated
 */
public interface TransactionListenerInterface {

    /**
     * Implement this method to set yout stuff when your expected transaction receipt is received.
     * @param transactionReceipt the org.web3j.protocol.core.methods.response.TransactionReceipt of the observed transaction.
     */
    public void notify(TxReceipt transactionReceipt);
}
