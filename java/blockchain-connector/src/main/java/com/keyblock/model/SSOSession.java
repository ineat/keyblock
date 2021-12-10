package com.keyblock.model;

import com.keyblock.util.Signable;

import java.time.Instant;

/**
 * Represents a SSO session shard accross blockchain nodes
 */
public class SSOSession extends Signable {

    private String sessionId;
    private String subjectAddress;
    private String issuerAddress;
    private long issuanceDateTimestamp;
    private long validityTime;

    public SSOSession(){}

    public SSOSession(String sessionId, String subjectAddress, String issuerAddress, long issuanceDateTimestamp, long validityTime) {
        this.sessionId = sessionId;
        this.subjectAddress = subjectAddress;
        this.issuerAddress = issuerAddress;
        this.issuanceDateTimestamp = issuanceDateTimestamp;
        this.validityTime = validityTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSubjectAddress() {
        return subjectAddress;
    }

    public void setSubjectAddress(String subjectAddress) {
        this.subjectAddress = subjectAddress;
    }

    public String getIssuerAddress() {
        return issuerAddress;
    }

    public void setIssuerAddress(String issuerAddress) {
        this.issuerAddress = issuerAddress;
    }

    public long getIssuanceDateTimestamp() {
        return issuanceDateTimestamp;
    }

    public void setIssuanceDateTimestamp(long issuanceDateTimestamp) {
        this.issuanceDateTimestamp = issuanceDateTimestamp;
    }

    /**
     * Get the session validity time, in seconds
     * @return validityTime this validity time, in seconds
     */
    public long getValidityTime() {
        return validityTime;
    }

    /**
     * Set the session validity time, in seconds
     * @param validityTime this validity time, in seconds
     */
    public void setValidityTime(long validityTime) {
        this.validityTime = validityTime;
    }

    /**
     * Special toString to get text data of a SSOSession to be used to sign
     * @return a String representation of SSOSession to be signed as session content
     */
    public String getSignatureDataString() {
        StringBuffer dataString = new StringBuffer();
        dataString.append(sessionId.toLowerCase());
        dataString.append(subjectAddress.toLowerCase());
        dataString.append(issuerAddress.toLowerCase());
        dataString.append(issuanceDateTimestamp);
        dataString.append(validityTime);
        return dataString.toString();
    }

    /**
     * Check if a session is active
     * @return true if the session is active, false otherwise
     */
    public boolean isActive() {
        return Instant.ofEpochSecond(this.getIssuanceDateTimestamp()+this.getValidityTime())
                .isAfter(Instant.now());
    }
}