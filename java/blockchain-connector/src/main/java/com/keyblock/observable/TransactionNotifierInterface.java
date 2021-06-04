package com.keyblock.observable;

import java.util.HashMap;

public interface TransactionNotifierInterface {

    public void subscribe(String transactionHash, TransactionListenerInterface listener);

    public HashMap<String, TransactionListenerInterface> getListeners();
}
