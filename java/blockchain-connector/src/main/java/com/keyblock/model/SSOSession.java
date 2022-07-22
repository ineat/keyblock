package com.keyblock.model;

import com.keyblock.util.Signable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Represents a SSO session shard across blockchain nodes
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SSOSession extends Signable {

    private String sessionId;
    private String subjectAddress;
    private String issuerAddress;
    private long issuanceDateTimestamp;
    private long validityTime;

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