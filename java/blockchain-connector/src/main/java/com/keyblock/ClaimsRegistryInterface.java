package com.keyblock;

import com.keyblock.model.Claim;
import com.keyblock.model.TxReceipt;
import com.keyblock.observable.TransactionNotifierInterface;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface ClaimsRegistryInterface extends TransactionNotifierInterface {

    /**
     * Read a @com.keyblock.Claim from smart contract
     * @param subjectAddress address of user
     * @param claimId key of @com.keyblock.Claim
     * @return the @com.keyblock.Claim that represents the stored value, null il not found
     */
    public Claim getClaim(String subjectAddress, String claimId);

    /**
     * Synchronous call smart contract function SimpleClaimsRegistry.setClaim to create a new @com.keyblock.Claim and wait for result.
     * @param claim the claim to create
     * @return the @com.keyblock.Claim created
     */
    public TxReceipt setClaimSync(Claim claim) throws IOException, ExecutionException, InterruptedException;

    /**
     * Asynchronous call smart contract function SimpleClaimsRegistry.setClaim to create a new @com.keyblock.Claim and only send transaction.
     * @param claim the claim to create
     * @return the transaction hash
     */
    public String setClaimAsync(Claim claim) throws IOException, ExecutionException, InterruptedException;

    /**
     * Wait for a transaction to be validated. The call is blocking until receipt is created.
     * @param transactionHash the hash of transaction to wait for
     * @return the org.web3j.protocol.core.methods.response.TransactionReceipt of given transaction hash
     */
     public TxReceipt waitForReceipt(String transactionHash);

}
