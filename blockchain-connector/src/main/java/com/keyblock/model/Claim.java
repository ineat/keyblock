package com.keyblock.model;

import com.keyblock.util.Signable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Represent a Claim, that handles assertions about a user on the blockchain
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Claim extends Signable {
    private String subjectAddress;
    private String issuerAddress;
    private Instant issuedAt;
    private String key;
    private String value;

    public Claim(String subjectAddress, String issuerAddress, Instant issuedAt, String signature, String key, String value) {
        this.subjectAddress = subjectAddress;
        this.issuerAddress = issuerAddress;
        this.issuedAt = issuedAt;
        this.signature = signature;
        this.key = key;
        this.value = value;
    }

    @Override
    public String getSignatureDataString() {
        StringBuffer dataString = new StringBuffer();
        dataString.append(subjectAddress.toLowerCase());
        dataString.append(issuerAddress.toLowerCase());
        dataString.append(key.toLowerCase());
        dataString.append(value.toLowerCase());
        return dataString.toString();
    }
}
