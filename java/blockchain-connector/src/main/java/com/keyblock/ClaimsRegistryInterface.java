package com.keyblock;

public interface ClaimsRegistryInterface {

    /**
     * Read a @Claim from smart contract
     * @param subjectAddress address of user
     * @param claimId key of @Claim
     * @return the @Claim that represents the stored value, null il not found
     */
    public Claim getClaim(String subjectAddress, String claimId);

    /**
     * Call smart contract function SimpleClaimsRegistry.setClaim to create a new @Claim
     * @param subjectAddress user address the @Claim is related to
     * @param claimId key of @Claim to set
     * @param claimValue value of @Claim to set
     */
    public void setClaim(String subjectAddress, String claimId, String claimValue);
}
