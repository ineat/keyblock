package com.keyblock.test;

import com.keyblock.util.SignUtil;
import org.junit.jupiter.api.Test;

import java.security.SignatureException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignUtilTest {

    private final String data = "You are going to use your private key to sign this data and be authenticated for claim holder contract.";
    private final String signerAddress = "0x2da92f7beaB763a7E975aecCe8F85B6F54be231e";
    private final String privateKey = "9fe18402c676e7aca98ac2ceb3a3c13fcab84cffa6814a8d996b73608edb6ad6";
    private final String signature = "0x9b8950d20fe1700ed99bf69be30a7fcacdfc542d06e51bee56d796c29956ac4011086a5ecb2c17969e40212bb027485b3650d1f12bee5f30a775fd58b0d6a2c31c";

    @Test
    public void testSignature() {
        String sig = SignUtil.sign(data, privateKey);
        assertEquals(sig, signature);
    }

    @Test
    public void testRecover() throws SignatureException {
       assertTrue(SignUtil.checkSignature(data, signature, signerAddress));
    }
}
