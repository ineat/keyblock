package com.keyblock.model;

import java.time.Instant;

public class Claim {
    private String subjectAddress;
    private String issuerAddress;
    private Instant issuedAt;
    private String signature;
    private String key;
    private String value;

    public Claim() {
    }

    public Claim(String subjectAddress, String issuerAddress, Instant issuedAt, String signature, String key, String value) {
        this.subjectAddress = subjectAddress;
        this.issuerAddress = issuerAddress;
        this.issuedAt = issuedAt;
        this.signature = signature;
        this.key = key;
        this.value = value;
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

    public Instant getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Instant issuedAt) {
        this.issuedAt = issuedAt;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}