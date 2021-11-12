package com.keyblock.api;

public class SSOSession {

    private String sessionId;
    private String subjectAddress;
    private String issuerAddress;
    private long issuanceDateTimestamp;
    private long endValidityDateTimestamp;
    private String signature;

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

    public long getEndValidityDateTimestamp() {
        return endValidityDateTimestamp;
    }

    public void setEndValidityDateTimestamp(long endValidityDateTimestamp) {
        this.endValidityDateTimestamp = endValidityDateTimestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}