package com.keyblock.test;

import com.keyblock.SSOSessionConnector;
import com.keyblock.model.SSOSession;
import com.keyblock.model.TxReceipt;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SSOSessionTest {

    SSOSessionConnector ssoSessionConnector;

    private String subjectAddress = "0x4769F9301c7DDA7c8BbE7324C9A6D9A09699B6AE";
    private String wrongSubjectAddress = "0xabababababababababababababababababababab";
    private String sessionId = "73dc5016-2a64-46d2-a4fa-39d34fa0ad99";
    private long validityTime = 3600;

    @BeforeAll
    public void init() {
        ssoSessionConnector = new SSOSessionConnector(
                "https://ropsten.infura.io/v3/e6293df88f0a4648ad7624dad8822a98"
                ,"0x01A824dd7551c905aeb9d18E9F7B086FCadA3028"
                ,"0x41f6B225846863E3C037e92F229cD40f5d575258"
                ,"85d4fc54c9c6de275f5b0ac1a975657ed95d3959cdb97edc9da953bf1a75c723"
        );
    }

    @Test
    public void whenCreateSession_ThenOK() throws Exception {

        SSOSession session = new SSOSession();
        session.setSessionId(sessionId);
        session.setSubjectAddress(subjectAddress);
        session.setValidityTime(validityTime);

        TxReceipt receipt = ssoSessionConnector.createSessionSync(session);
        assertNotNull(receipt);
    }

    @Test
    public void whenRevokeSession_ThenOK() throws Exception {
        TxReceipt receipt = ssoSessionConnector.revokeSessionSync(subjectAddress);
        assertNotNull(receipt);
    }

    @Test
    public void whenGetSessionForUser_ThenGetSession() throws Exception {
        SSOSession session = ssoSessionConnector.getSession(subjectAddress);
        assertEquals(session.getSubjectAddress().toUpperCase(), subjectAddress.toUpperCase());
    }

    @Test
    public void whenGetSessionForUnknownUser_ThenNoSession() throws Exception {
        SSOSession session = ssoSessionConnector.getSession(wrongSubjectAddress);
        assertEquals(session.getSubjectAddress().toUpperCase(), SSOSessionConnector.ADDRESS_ZERO);
    }

    @Test
    public void whenCheckActiveSession_ThenActive() throws Exception {

        // create an active session
        SSOSession session = new SSOSession();
        session.setSessionId(sessionId);
        session.setSubjectAddress(subjectAddress);
        session.setValidityTime(validityTime);

        TxReceipt receipt = ssoSessionConnector.createSessionSync(session);
        assertNotNull(receipt); // valid session has been set

        assertTrue(ssoSessionConnector.isSessionActive(subjectAddress));
    }

    @Test
    public void whenCheckInactiveSession_ThenInactive() throws Exception {

        // create an inactive session
        SSOSession session = new SSOSession();
        session.setSessionId(sessionId);
        session.setSubjectAddress(subjectAddress);
        session.setValidityTime(0);

        TxReceipt receipt = ssoSessionConnector.createSessionSync(session);
        assertNotNull(receipt); // valid session has been set

        assertFalse(ssoSessionConnector.isSessionActive(subjectAddress));
    }
}