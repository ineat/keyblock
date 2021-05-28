package com.keyblock;

import com.keyblock.observable.TransactionListenerInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class TxReceiver implements TransactionListenerInterface {

    private static final Logger log = LogManager.getLogger(TxReceiver.class);

    private ClaimsRegistryInterface registry;

    public TxReceiver(ClaimsRegistryInterface registry) {
        this.registry=registry;
    }

    @Override
    public void notify(TransactionReceipt transactionReceipt) {
        log.info("Get receipt: "+transactionReceipt.getBlockNumber());
    }

}
