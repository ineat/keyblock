package com.keyblock.test;

import com.keyblock.SSOSessionConnector;
import com.keyblock.model.SSOSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SSOSessionTest {

    SSOSessionConnector contract;

    private String subjectAddress = "0x4769F9301c7DDA7c8BbE7324C9A6D9A09699B6AE";
    private String wrongSubjectAddress = "0xabababababababababababababababababababab";
    private String sessionId = "73dc5016-2a64-46d2-a4fa-39d34fa0ad99";
    private long endValidityTimestamp = 1668329202;

    @BeforeAll
    public void init() {
        contract = new SSOSessionConnector(
                "https://ropsten.infura.io/v3/e6293df88f0a4648ad7624dad8822a98"
                ,"0x05603AFa90048DAEB8Bd52933bC60F58E3ba1b3A"
                ,"0x2da92f7beaB763a7E975aecCe8F85B6F54be231e"
                ,"9fe18402c676e7aca98ac2ceb3a3c13fcab84cffa6814a8d996b73608edb6ad6"
        );
    }

    @Test
    public void createSessionTest() throws IOException, ExecutionException, InterruptedException {

        SSOSession session = new SSOSession();
        session.setSessionId(sessionId);
        session.setSubjectAddress(subjectAddress);
        session.setEndValidityDateTimestamp(endValidityTimestamp);

        String txHash = contract.createSession(session);
        assertNotNull(txHash);
    }

    @Test
    public void revokeSessionTest() throws IOException, ExecutionException, InterruptedException {
        String txHash = contract.revokeSession(subjectAddress);
        assertNotNull(txHash);
    }

    @Test
    public void getSessionTest() throws Exception {
        SSOSession session = contract.getSession(subjectAddress);
        assertEquals(session.getSubjectAddress().toUpperCase(), subjectAddress.toUpperCase());
    }

    @Test
    public void getNoSessionTest() throws Exception {
        SSOSession session = contract.getSession(wrongSubjectAddress);
        assertEquals(session.getSubjectAddress().toUpperCase(), "0X0000000000000000000000000000000000000000");
    }
}