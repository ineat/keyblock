package com.keyblock.util;


import org.web3j.crypto.*;


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

    /**
     * Retreive an ECDSA public key from the related private key
     * @param privateKey the private key
     * @return the public key
     */
    public static String getPublicKey(String privateKey) {
        BigInteger privateKeyInBT = new BigInteger(privateKey, 16);
        ECKeyPair aPair = ECKeyPair.create(privateKeyInBT);
        BigInteger publicKeyInBT = aPair.getPublicKey();
        return publicKeyInBT.toString();
    }

    /**
     * Convert an ASCII string to its hexadecimal value
     * @param asciiString the input text data
     * @param prefix true to add the "0x" prefix to the result
     * @return the hexadecimal value of input string
     */
    public static String asciiToHex(String asciiString, boolean prefix) {
        char[] ch = asciiString.toCharArray();
        StringBuilder builder = new StringBuilder();

        if(prefix)
            builder.append("0x");

        for (char c : ch) {
            int i = (int) c;
            builder.append(Integer.toHexString(i).toUpperCase());
        }

        return builder.toString();
    }

    /**
     * Convert a hexadecimal representation of a string to its ASCII value
     * @param hexString the input hexadecimal data
     * @return the ASCII value of the input hexadecimal string
     */
    public static String hexToAscii(String hexString) {

        if(hexString.startsWith("0x")) {
            hexString = hexString.substring(2);
        }

        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < hexString.length(); i += 2) {
            String str = hexString.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }

        return output.toString();
    }

    /**
     * Converts an hexadecimal string to an array of bytes
     * @param hexString the hexadecimal input string
     * @return the bytes array that represents the hexadecimal input string
     */
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

    private static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }


    /**
     * See also Numeric.toHexString
     * @param byteArray
     * @return
     */
    public static String bytesArrayToHexString(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString();
    }

}
