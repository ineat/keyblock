package com.keyblock.model;

import com.keyblock.util.Signable;

public class SSOSession extends Signable {

    private String sessionId;
    private String subjectAddress;
    private String issuerAddress;
    private long issuanceDateTimestamp;
    private long endValidityDateTimestamp;

    public SSOSession(){}

    public SSOSession(String sessionId, String subjectAddress, String issuerAddress, long issuanceDateTimestamp, long endValidityDateTimestamp) {
        this.sessionId = sessionId;
        this.subjectAddress = subjectAddress;
        this.issuerAddress = issuerAddress;
        this.issuanceDateTimestamp = issuanceDateTimestamp;
        this.endValidityDateTimestamp = endValidityDateTimestamp;
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

    public long getEndValidityDateTimestamp() {
        return endValidityDateTimestamp;
    }

    public void setEndValidityDateTimestamp(long endValidityDateTimestamp) {
        this.endValidityDateTimestamp = endValidityDateTimestamp;
    }

    public String getSignatureDataString() {
        StringBuffer dataString = new StringBuffer();
        dataString.append(sessionId.toLowerCase());
        dataString.append(subjectAddress.toLowerCase());
        dataString.append(issuerAddress.toLowerCase());
        dataString.append(issuanceDateTimestamp);
        dataString.append(endValidityDateTimestamp);
        return dataString.toString();
    }
}