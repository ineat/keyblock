package com.keyblock;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

public interface ClaimsRegistryInterface {

    /**
     * Read a @com.keyblock.Claim from smart contract
     * @param subjectAddress address of user
     * @param claimId key of @com.keyblock.Claim
     * @return the @com.keyblock.Claim that represents the stored value, null il not found
     */
    public Claim getClaim(String subjectAddress, String claimId);

    /**
     * Call smart contract function SimpleClaimsRegistry.setClaim to create a new @com.keyblock.Claim
     * @param subjectAddress user address the @Claim is related to
     * @param claimId key of @com.keyblock.Claim to set
     * @param claimValue value of @com.keyblock.Claim to set
     */
    @Deprecated
    public void setClaim(String subjectAddress, String claimId, String claimValue);

    /**
     * Synchronous call smart contract function SimpleClaimsRegistry.setClaim to create a new @com.keyblock.Claim and wait for result.
     * @param subjectAddress user address the @Claim is related to
     * @param claimId key of @com.keyblock.Claim to set
     * @param claimValue value of @com.keyblock.Claim to set
     * @return the @com.keyblock.Claim created
     */
    public TransactionReceipt setClaimSync(String subjectAddress, String claimId, String claimValue);

    /**
     * Asynchrinous call smart contract function SimpleClaimsRegistry.setClaim to create a new @com.keyblock.Claim and only send transaction.
     * @param subjectAddress user address the @Claim is related to
     * @param claimId key of @com.keyblock.Claim to set
     * @param claimValue value of @com.keyblock.Claim to set
     * @return the transaction hash
     */
    public String setClaimAsync(String subjectAddress, String claimId, String claimValue);
}
