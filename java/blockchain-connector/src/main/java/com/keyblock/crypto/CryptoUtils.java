package com.keyblock.crypto;

import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;

public class CryptoUtils {
    /**
     * Retreive an ECDSA public key from the related private key
     * @param privateKeyInHex the private key, encoded in hexadecimal
     * @return the public key in hexadecimal
     */
    public static String getPublicKeyInHex(String privateKeyInHex) {
        BigInteger privateKeyInBT = new BigInteger(privateKeyInHex, 16);
        ECKeyPair aPair = ECKeyPair.create(privateKeyInBT);
        BigInteger publicKeyInBT = aPair.getPublicKey();
        String sPublickeyInHex = publicKeyInBT.toString(16);
        return sPublickeyInHex;
    }
}
