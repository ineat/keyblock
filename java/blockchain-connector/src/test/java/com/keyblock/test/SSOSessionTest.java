package com.keyblock.test;

import com.keyblock.SSOSessionConnector;
import com.keyblock.api.SSOSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SSOSessionTest {

    SSOSessionConnector contract;

    private String subjectAddress = "0x4769F9301c7DDA7c8BbE7324C9A6D9A09699B6AE";
    private String sessionId = "73dc5016-2a64-46d2-a4fa-39d34fa0ad99";
    private long endValidityTimestamp = 1668329202;

    @BeforeAll
    public void init() {
        contract = new SSOSessionConnector(
                "HTTP://127.0.0.1:7545"
                ,"0xd9E7721EF32EC60dcA60DFAA1518BD9934ebf5Eb"
                ,"0x309B2Df61A8Fd7B3550D9ca6e78A0462960D476F"
                ,"5a83ebabd5b142d93151ea5cacf1f9407ed569428b30471df57904c6c12f85c2"
        );
    }

    @Test
    public void createSessionTest() throws IOException, ExecutionException, InterruptedException {
        contract.createSession(sessionId, subjectAddress, endValidityTimestamp, "0x");
    }

    @Test
    public void revokeSessionTest() throws IOException, ExecutionException, InterruptedException {
        contract.revokeSession(subjectAddress);
    }

    @Test
    public void getSessionTest() throws Exception {
        SSOSession session = contract.getSession(subjectAddress);
        assertEquals(session.getSubjectAddress().toUpperCase(), subjectAddress.toUpperCase());
    }
}