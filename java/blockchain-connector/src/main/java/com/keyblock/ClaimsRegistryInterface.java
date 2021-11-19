package com.keyblock;

import com.keyblock.model.Claim;
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
     * @param subjectAddress user address the @Claim is related to
     * @param claimId key of @com.keyblock.Claim to set
     * @param claimValue value of @com.keyblock.Claim to set
     * @return the @com.keyblock.Claim created
     */
    public com.keyblock.model.TransactionReceipt setClaimSync(String subjectAddress, String claimId, String claimValue) throws IOException, ExecutionException, InterruptedException;

    /**
     * Asynchronous call smart contract function SimpleClaimsRegistry.setClaim to create a new @com.keyblock.Claim and only send transaction.
     * @param subjectAddress user address the @Claim is related to
     * @param claimId key of @com.keyblock.Claim to set
     * @param claimValue value of @com.keyblock.Claim to set
     * @return the transaction hash
     */
    public String setClaimAsync(String subjectAddress, String claimId, String claimValue) throws IOException, ExecutionException, InterruptedException;

    /**
     * Wait for a transaction to be validated. The call is blocking until receipt is created.
     * @param transactionHash the hash of transaction to wait for
     * @return the org.web3j.protocol.core.methods.response.TransactionReceipt of given transaction hash
     */
    public com.keyblock.model.TransactionReceipt waitForReceipt(String transactionHash);

}
