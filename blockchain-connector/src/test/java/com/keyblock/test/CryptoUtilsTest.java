package com.keyblock.test;

import com.keyblock.util.CryptoUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CryptoUtilsTest {

    private final String asciiString = "This is a test string !";
    private final String hexString = "546869732069732061207465737420737472696e672021";

    @Test
    public void whenComputePublicKey_thenOK() {
        String privateKey = "85d4fc54c9c6de275f5b0ac1a975657ed95d3959cdb97edc9da953bf1a75c723";
        String publicKey = "989954188480680428188039917503450661451361154074515013410674837831042273637539713765335296799907454064952437298661675075241163102274378810626833899953342";
        String computedPublicKey = CryptoUtils.getPublicKey(privateKey);
        assertEquals(publicKey, computedPublicKey);
    }

    @Test
    public void whenComputePublicKeyInHex_thenOK() {
        String privateKey = "85d4fc54c9c6de275f5b0ac1a975657ed95d3959cdb97edc9da953bf1a75c723";
        String publicKeyHex = "12e6cb97527287a6d9e2ac611ab260d368583896aa7a4bfa94d6eabf66377bb6fa4ed703b20192e33bec76d2e73ff0eb31cd32684ff30292e3cdea770ee99cbe";
        String computedPublicKey = CryptoUtils.getPublicKeyInHex(privateKey);
        assertEquals(publicKeyHex, computedPublicKey);
    }


    @Test
    public void testStringToHex() {
        String hex = CryptoUtils.asciiToHex(asciiString, true);
        assertEquals(hex.toLowerCase(), "0x"+hexString.toLowerCase());

        hex = CryptoUtils.asciiToHex(asciiString, false);
        assertEquals(hex.toLowerCase(), hexString.toLowerCase());
    }

    @Test
    public void testHexToString() {
        String ascii = CryptoUtils.hexToAscii(hexString);
        assertEquals(ascii, asciiString);
    }
}
