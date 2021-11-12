package com.keyblock;

import org.web3j.crypto.*;

import java.math.BigInteger;
import java.security.SignatureException;

public class RecoverUtil {

    public static void main(String... args) throws SignatureException {
        final String data = "hello";
        final String signature = "0x085aae56ef110063408eca4451ec65ccd4792a44cb3ab8f21aadcb9b688a4cbb6f9b6bb8bcf28d4b49e0bc4317415f298a34a3c295823fbf135ccdf99246671d1c";
        final String userEthAddress = "0x41f6B225846863E3C037e92F229cD40f5d575258";

        final boolean result = recover(data, signature, userEthAddress);
        System.out.println("Signature checked: " +result);
    }

    public static boolean recover(String textMessage, String hexStringSignature, String hexStringEthAddress) throws SignatureException {
        String r = hexStringSignature.substring(2, 66);
        String s = hexStringSignature.substring(66, 130);
        String v = hexStringSignature.substring(130, 132);

        Sign.SignatureData signatureData = new Sign.SignatureData(hexStringToBytesArray(v), hexStringToBytesArray(r), hexStringToBytesArray(s));

        BigInteger pubKey = Sign.signedPrefixedMessageToKey(textMessage.getBytes(), signatureData);

        String recover = Keys.getAddress(pubKey);
        recover = "0x"+recover;

        return recover.equalsIgnoreCase(hexStringEthAddress);
    }

    public static byte[] hexStringToBytesArray(String hexString) {

        if(hexString.startsWith("0x")) {
            hexString = hexString.substring(2);
        }

        if ((hexString.length() % 2) != 0) {
            throw new IllegalArgumentException("Invalid hex string (length % 2 != 0)");
        }
        byte[] array = new byte[hexString.length() / 2];
        for (int i = 0, arrayIndex = 0; i < hexString.length(); i += 2, arrayIndex++) {
            array[arrayIndex] = Integer.valueOf(hexString.substring(i, i + 2), 16).byteValue();
        }
        return array;
    }
}
