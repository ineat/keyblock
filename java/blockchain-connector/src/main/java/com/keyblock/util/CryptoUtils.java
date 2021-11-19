package com.keyblock.util;

import com.keyblock.model.Claim;
import com.keyblock.model.Signature;
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

    public static Signature signClaim(Claim claim, String privateKey) {

        // 1. concatenate issuerAddress + subjectAddress + key + value
        StringBuffer plainData = new StringBuffer(claim.getIssuerAddress())
                .append(claim.getSubjectAddress())
                .append(claim.getKey())
                .append(claim.getValue());
        byte[] data = plainData.toString().getBytes();

        Signature signatureResult = new Signature();
        signatureResult.setSignerPublicKey(CryptoUtils.getPublicKeyInHex(privateKey));

        Credentials credentials = Credentials.create(privateKey);
        signatureResult.setSignerAddress(credentials.getAddress());

        // 2. data will be sha3 hashed
        // 3. then signed
        Sign.SignatureData signature = Sign.signMessage(data, credentials.getEcKeyPair());

        signatureResult.setR(signature.getR());
        signatureResult.setS(signature.getS());
        signatureResult.setV(signature.getV());

        /*
        System.out.println("Sha3: " + Numeric.toHexString(Hash.sha3(data)));
        System.out.println("Data: " + Numeric.toHexString(data));
        System.out.println("Address: " + credentials.getAddress());
        System.out.println("R: " + Numeric.toHexString(signature.getR()));
        System.out.println("S: " + Numeric.toHexString(signature.getS()));
        System.out.println("V: " + Numeric.toHexString(signature.getV()));*/

        return signatureResult;
    }
}
