package com.keyblock.test;

import com.keyblock.ClaimsRegistryInterface;
import com.keyblock.api.TransactionReceipt;
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

    private com.keyblock.api.TransactionReceipt receipt;

    public TestTxListener(ClaimsRegistryInterface registry) {
        this.registry=registry;
        this.testClaimValue = UUID.randomUUID().toString();
        this.receipt = null;

    }

    @Override
    public void notify(TransactionReceipt transactionReceipt) {
        log.info("Get receipt: "+transactionReceipt.getBlockNumber());
        this.receipt = transactionReceipt;
    }

    public String getTestClaimValue() {
        return testClaimValue;
    }

    public void setTestClaimValue(String testClaimValue) {
        this.testClaimValue = testClaimValue;
    }

    public Future<com.keyblock.api.TransactionReceipt> getReceipt() {
        CompletableFuture<com.keyblock.api.TransactionReceipt> completableFuture = new CompletableFuture<>();
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
