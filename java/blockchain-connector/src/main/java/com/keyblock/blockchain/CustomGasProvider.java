package com.keyblock.blockchain;

import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

public class CustomGasProvider implements ContractGasProvider {

    @Override
    public BigInteger getGasPrice(String contractFunc) {
        return DefaultGasProvider.GAS_PRICE;
    }

    @Override
    public BigInteger getGasLimit(String contractFunc) {
        // TODO find a way to compute it more accurately
        return BigInteger.valueOf(600000L);
    }

    @Override
    @Deprecated
    public BigInteger getGasPrice() {
        return null;
    }

    @Override
    @Deprecated
    public BigInteger getGasLimit() {
        return null;
    }
}
