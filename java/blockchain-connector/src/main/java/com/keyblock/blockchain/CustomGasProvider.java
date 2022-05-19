package com.keyblock.blockchain;

import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

/**
 * Gas provider to compute gas price and gas limit for a function call
 */
public class CustomGasProvider implements ContractGasProvider {

    /**
     * Compute gas price for a function call
     * @param contractFunc the function call encoded with org.web3j.abi.FunctionEncoder#encode(org.web3j.abi.datatypes.Function)
     * @return the most accurate gas price for this call
     */
    @Override
    public BigInteger getGasPrice(String contractFunc) {
        // boost the GAS Price to ensure that the transaction is validated shortly
        return DefaultGasProvider.GAS_PRICE.multiply(BigInteger.valueOf(500l));
    }

    /**
     * Compute gas limit for a function call
     * @param contractFunc the function call encoded with org.web3j.abi.FunctionEncoder#encode(org.web3j.abi.datatypes.Function)
     * @return the most accurate gas limit for this call
     */
    @Override
    public BigInteger getGasLimit(String contractFunc) {
        // TODO find a way to compute it more accurately
        return BigInteger.valueOf(800000L);
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
