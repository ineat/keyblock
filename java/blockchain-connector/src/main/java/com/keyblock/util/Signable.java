package com.keyblock.util;

/**
 * Provides data and function for objects that can be signed and checked
 */
public abstract class Signable {

    protected String signature;

    /**
     * The signature
     * @return the signature hexadecimal data
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Set signature
     * @param signature signature hexadecimal data
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * A Signable object must implements this method. It should provide a plain text string, built from data attributes, that is the data to be signed which represents this object.
     * @return the hexadecimal string signature data
     */
    public abstract String getSignatureDataString();

    /**
     * Use data provided by getSignatureDataString() to create a signature, then stores it into object.
     * @param privateKey the private key to be used to sign
     */
    public void sign(String privateKey) {
        String dataToSign = this.getSignatureDataString();
        String signature = SignUtil.sign(dataToSign, privateKey);
        this.signature = signature;
    }

    /**
     * Check if an object's signature is correct. It will use data provided by getSignatureDataString() to create a signature, then recover public key used to sign the object and finally compare it with expected signer address.
     * @param expectedIssuerAddress the supposed address that has sign the object
     * @return true is the object was sign by expected issuer address, false otherwise
     */
    public boolean checkSignature(String expectedIssuerAddress) {
        String dataToSign = this.getSignatureDataString();
        return SignUtil.checkSignature(dataToSign, this.signature, expectedIssuerAddress);
    }

}
