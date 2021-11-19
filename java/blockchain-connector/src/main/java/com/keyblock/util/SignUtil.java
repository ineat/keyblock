package com.keyblock.util;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.security.SignatureException;

public abstract class SignUtil {

    /**
     * Sign a data with given private key
     * @param data plain text data
     * @param privateKey the private key to use to sign it
     * @return the hexadecimal signature
     */
    public static String sign(String data, String privateKey){
        Credentials creds = Credentials.create(privateKey, CryptoUtils.getPublicKeyInHex(privateKey));
        ECKeyPair keyPair = creds.getEcKeyPair();
        data = CryptoUtils.asciiToHex(data, true);
        Sign.SignatureData signatureData = Sign.signPrefixedMessage(CryptoUtils.hexStringToBytesArray(data), keyPair);

        return new StringBuffer()
                .append("0x")
                .append(Numeric.toHexString(signatureData.getR()).replace("0x",""))
                .append(Numeric.toHexString(signatureData.getS()).replace("0x",""))
                .append(Numeric.toHexString(signatureData.getV()).replace("0x",""))
                .toString();
    }

    /**
     * Check a signature
     * @param data the plain ascii text that was signed
     * @param signature the signature
     * @param supposedSignerAddress the address of user that was supposed to sign the data
     * @return true is given address is the signer, false if recovered address is an other address or in cas of error during check
     */
    public static boolean checkSignature(String data, String signature, String supposedSignerAddress) {

        try {
            return RecoverUtil.recover(data, signature, supposedSignerAddress);
        }
        catch(Exception e) {
            return false;
        }
    }
}
