package com.keyblock.api;

import java.nio.charset.StandardCharsets;

public class Signature {
    private String signerAddress;
    private String signerPublicKey;
    private String initialDataInHex;

    private byte[] R;
    private byte[] S;
    private byte[] V;

    public String getSignerAddress() {
        return signerAddress;
    }

    public void setSignerAddress(String signerAddress) {
        this.signerAddress = signerAddress;
    }

    public String getSignerPublicKey() {
        return signerPublicKey;
    }

    public void setSignerPublicKey(String signerPublicKey) {
        this.signerPublicKey = signerPublicKey;
    }

    public String getInitialDataInHex() {
        return initialDataInHex;
    }

    public void setInitialDataInHex(String initialDataInHex) {
        this.initialDataInHex = initialDataInHex;
    }

    public byte[] getR() {
        return R;
    }

    public void setR(byte[] r) {
        R = r;
    }

    public byte[] getS() {
        return S;
    }

    public void setS(byte[] s) {
        S = s;
    }

    public byte[] getV() {
        return V;
    }

    public void setV(byte[] v) {
        V = v;
    }

    public String toString() {
        return (new StringBuffer())
                .append("R: ").append(this.R)
                .append("S: ").append(this.S.toString())
                .append("V: ").append(this.V.toString())
                .toString();
    }
}
