package com.keyblock;

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
    public void setClaim(String subjectAddress, String claimId, String claimValue);
}
