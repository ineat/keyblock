package com.keyblock.util;

import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;

import java.math.BigInteger;
import java.security.SignatureException;

/**
 * Provides a utility service to check an Ethereum signature
 */
public abstract class RecoverUtil {

    /**
     * Given a message, the signature of this message and an Ethereum address, checks if the address is the signer
     * @param textMessage The message that was signed, in plain text.
     * @param hexStringSignature The signature, in hexadecimal string
     * @param hexStringEthAddress The Ethereum address, in hexadecimal string
     * @return true if Ethereum address is the signer of message that creates the signature
     * @throws SignatureException if an error occurs when checking signature
     */
    public static boolean recover(String textMessage, String hexStringSignature, String hexStringEthAddress) throws SignatureException {

        if(hexStringSignature.length() != 132 || !hexStringSignature.startsWith("0x")) {
            throw new SignatureException("Signature must be an hexadecimal string starting with 0x + 130 characters");
        }

        // Split signature in 3 parts
        // The R, S part of any ECDSA signature, and the specific Ethereum V part.
        // Signature is as follows :
        // [0-1]: hexadecimal prefix (0x), skipped when splitting
        // [2-65]: R, 64 hexa characters, 32 bytes
        // [66-129]: S, 64 hexa characters, 32 bytes
        // [130-131]: V, 2 hexa characters, 1 byte
        String r = hexStringSignature.substring(2, 66);
        String s = hexStringSignature.substring(66, 130);
        String v = hexStringSignature.substring(130, 132);

        // Then we need to cast hexadecimal strings R, S & V into bytes arrays to create a signature object
        Sign.SignatureData signatureData = new Sign.SignatureData(hexStringToBytesArray(v), hexStringToBytesArray(r), hexStringToBytesArray(s));

        // Use Web3j framework to retrieve public key that has signed the message. Input message has to be cast from decimal alphabet to bytes array
        // Then, message will be wrapped as follows:
        // 1. message = "\u0019Ethereum Signed Message:\n" + message.length + message
        // 2. message = sha3(message)
        BigInteger pubKey = Sign.signedPrefixedMessageToKey(textMessage.getBytes(), signatureData);

        // Then retrieve the Ethereum address derived from the public key
        String recover = Keys.getAddress(pubKey);

        // Add the hexadecimal prefix to turn it back to usual Ethereum address
        recover = "0x" + recover;

        // The given address should match the parameter
        return recover.equalsIgnoreCase(hexStringEthAddress);
    }

    /**
     * Convert an hexadecimal string into an array of bytes
     * @param hexString the hexadecimal string to convert
     * @return the resulted bytes array
     */
    public static byte[] hexStringToBytesArray(String hexString) {

        // Remove hexadecimal prefix
        if (hexString.startsWith("0x")) {
            hexString = hexString.substring(2);
        }

        // Check that remaining characters number is even, to be able to group them by 2, for a single byte
        if ((hexString.length() % 2) != 0) {
            throw new IllegalArgumentException("Invalid hex string (length % 2 != 0)");
        }

        // Resulted bytes array length will be half of initial hexadecimal characters number
        byte[] array = new byte[hexString.length() / 2];

        // For each group of 2 hexa characters, convert it into its byte value
        for (int i = 0, arrayIndex = 0; i < hexString.length(); i += 2, arrayIndex++) {
            array[arrayIndex] = Integer.valueOf(hexString.substring(i, i + 2), 16).byteValue();
        }
        return array;
    }
}