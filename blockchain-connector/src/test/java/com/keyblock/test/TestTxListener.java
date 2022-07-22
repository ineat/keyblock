package com.keyblock.test;

import com.keyblock.ClaimsRegistryInterface;
import com.keyblock.model.TxReceipt;
import com.keyblock.observable.TransactionListenerInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Test class for Transaction listener/notifier observable pattern
 */
public class TestTxListener implements TransactionListenerInterface {

    private static final Logger log = LogManager.getLogger(TestTxListener.class);

    private ClaimsRegistryInterface registry;

    private String testClaimValue = "";

    private TxReceipt receipt;

    public TestTxListener(ClaimsRegistryInterface registry) {
        this.registry=registry;
        this.testClaimValue = UUID.randomUUID().toString();
        this.receipt = null;

    }

    @Override
    public void notify(TxReceipt transactionReceipt) {
        log.info("Get receipt: "+transactionReceipt.getBlockNumber());
        this.receipt = transactionReceipt;
    }

    public String getTestClaimValue() {
        return testClaimValue;
    }

    public void setTestClaimValue(String testClaimValue) {
        this.testClaimValue = testClaimValue;
    }

    public Future<TxReceipt> getReceipt() {
        CompletableFuture<TxReceipt> completableFuture = new CompletableFuture<>();
        log.info("waiting ...");
        while(this.receipt == null){
            log.info("not yet ...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("... received");
        completableFuture.complete(this.receipt);
        return completableFuture;
    }
}
