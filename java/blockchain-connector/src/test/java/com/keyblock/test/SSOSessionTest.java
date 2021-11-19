package com.keyblock.test;

import com.keyblock.SSOSessionConnector;
import com.keyblock.api.SSOSession;
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
                "HTTP://127.0.0.1:7545"
                ,"0x88eD8952B5BBB07B2bf5C21e107897D514465316"
                ,"0xbF440eB37F81BC3AD7fBe61DdA4C64EF0aF9A589"
                ,"9bfaae91a5c57277948c7e13a8829422e83d5f0a86a85a7855e2825d9ad92921"
        );
    }

    @Test
    public void createSessionTest() throws IOException, ExecutionException, InterruptedException {
        String txHash = contract.createSession(sessionId, subjectAddress, endValidityTimestamp, "0x");
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