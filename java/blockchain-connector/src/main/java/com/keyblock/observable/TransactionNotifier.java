package com.keyblock.observable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

/**
 * Obervable pattern to be notified for transaction validation
 */
public class TransactionNotifier {

    private static final Logger log = LogManager.getLogger(TransactionNotifier.class);

    private HashMap<String, TransactionListenerInterface> listeners = new HashMap<String, TransactionListenerInterface>();

    private Web3j web3j;

    protected void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }

    /**
     * Subscribe to the transaction notifier
     * @param transactionHash the hash of expected transaction
     * @param listener the com.keyblock.observable.TransactionListener that will be notified when transaction is validated
     */
    public void subscribe(String transactionHash, TransactionListenerInterface listener) {

        log.info("New subscription for: "+transactionHash);

        // When listener subscribes, first check if we can immediately get receipt and return it
        Optional<TransactionReceipt> transactionReceipt = checkReceipt(transactionHash);
        if(! transactionReceipt.isEmpty()) {
            log.info("Receipt found, notify listener");
           listener.notify(transactionReceipt.get());
        }
        else {
            log.info("Receipt not found, start waiting ...");
            // else subscribe for listening
            this.listeners.put(transactionHash, listener);
            startListeningThread(transactionHash);
        }
    }

    /**
     * Remove a listener
     * @param transactionHash the listening transaction
     * @param listener the listener to remove
     */
    public void unsubscribe(String transactionHash, TransactionListenerInterface listener) {
        if(this.listeners.get(transactionHash).equals(listener))
            this.listeners.remove(transactionHash);
    }

    /**
     * Check if transaction has already been validated
     * @param transactionHash the transactions hash
     * @return an java.util.Optional of org.web3j.protocol.core.methods.response.TransactionReceipt
     */
    private Optional<TransactionReceipt> checkReceipt(String transactionHash) {
        try {
            Optional<TransactionReceipt> transactionReceipt =
                    web3j.ethGetTransactionReceipt(transactionHash).send().getTransactionReceipt();
            return transactionReceipt;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param transactionHash
     * @param transactionReceipt
     */
    private void notify(String transactionHash, TransactionReceipt transactionReceipt) {
        TransactionListenerInterface listener = this.listeners.get(transactionHash);
        if(listener != null){
            listener.notify(transactionReceipt);
        }
    }

    /**
     * Start listening in background for a transaction to be validated
     * @param transactionHash the transaction hash
     */
    private void startListeningThread(String transactionHash) {
        TxListeningRunner runner = new TxListeningRunner(transactionHash, this.listeners.get(transactionHash), this.web3j);
       new Thread(runner).start();
    }

    /**
     * Creates a runner that wait for a transaction receipt to nitify a listener
     */
    private class TxListeningRunner implements Runnable {

        private String transactionHash;
        private TransactionListenerInterface listener;
        private Web3j web3j;

        /**
         * Creates the runner
         * @param transactionHash the hash of transaction to observe
         * @param listener the listener to be notified
         * @param web3j the web3 object with blockchain connection
         */
        public TxListeningRunner(String transactionHash, TransactionListenerInterface listener, Web3j web3j) {
            this.transactionHash = transactionHash;
            this.listener = listener;
            this.web3j = web3j;
        }

        @Override
        public void run() {

            log.info("Notifier thread started for "+this.transactionHash);

            // Wait for receipt
            TransactionReceiptProcessor receiptProcessor =
                    new PollingTransactionReceiptProcessor(this.web3j, TransactionManager.DEFAULT_POLLING_FREQUENCY,
                            TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);

            TransactionReceipt txReceipt = null;
            try {
                txReceipt = receiptProcessor.waitForTransactionReceipt(transactionHash);
                log.info("Block number: "+txReceipt.getBlockNumber());
                this.listener.notify(txReceipt);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (TransactionException e) {
                e.printStackTrace();
            }
        }
    }
}
