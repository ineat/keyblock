package com.keyblock;

import com.keyblock.blockchain.SmartContract;

import com.keyblock.model.SSOSession;
import com.keyblock.model.TxReceipt;
import com.keyblock.util.CryptoUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.abi.datatypes.*;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class SSOSessionConnector extends SmartContract implements SSOSessionInterface {

    private static final Logger log = LogManager.getLogger(SSOSessionConnector.class);
    public static final String ADDRESS_ZERO = "0X0000000000000000000000000000000000000000";
    private com.keyblock.contract.SSOSession contract;

    public SSOSessionConnector(String endpointUrl, String contractAddress, String ethereumAddress, String ethereumPrivateKey) {
        super(endpointUrl, contractAddress, ethereumAddress, ethereumPrivateKey);
        loadContract();
    }

    private void loadContract() {
        assert (web3j != null);
        assert (credentials != null);
        this.contract = com.keyblock.contract.SSOSession.load(connection.getContractAddress(), this.web3j, this.credentials, gasProvider);
        log.info("Contract loaded: "+contract.getContractAddress());
        try {
            log.info("Contract check: "+contract.isValid());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String createSessionAsync(SSOSession ssoSession) throws Exception {

        ssoSession.setIssuerAddress(this.connection.getEthereumAddress());

        // sign session
        ssoSession.sign(this.connection.getEthereumPrivateKey());

        // build function call
        Function function = new Function(
                "createSession",
                Arrays.asList(new Utf8String(ssoSession.getSessionId()), new Address(ssoSession.getSubjectAddress()), new Uint(BigInteger.valueOf(ssoSession.getValidityTime())), new DynamicBytes(CryptoUtils.hexStringToBytesArray(ssoSession.getSignature()))),
                Collections.emptyList());

        return callContractFunctionAsync(function);
    }

    @Override
    public String revokeSessionAsync(String subjectAddress) throws Exception {
        // build function call
        Function function = new Function(
                "revokeSession",
                Arrays.asList(new Address(subjectAddress)),
                Collections.emptyList());

        return callContractFunctionAsync(function);
    }

    @Override
    public TxReceipt createSessionSync(SSOSession ssoSession) throws Exception {

        ssoSession.setIssuerAddress(this.connection.getEthereumAddress());

        // sign session
        ssoSession.sign(this.connection.getEthereumPrivateKey());

        // build function call
        Function function = new Function(
                "createSession",
                Arrays.asList(new Utf8String(ssoSession.getSessionId()), new Address(ssoSession.getSubjectAddress()), new Uint(BigInteger.valueOf(ssoSession.getValidityTime())), new DynamicBytes(CryptoUtils.hexStringToBytesArray(ssoSession.getSignature()))),
                Collections.emptyList());

        return callContractFunctionSync(function);
    }

    @Override
    public TxReceipt revokeSessionSync(String subjectAddress) throws Exception {
        // build function call
        Function function = new Function(
                "revokeSession",
                Arrays.asList(new Address(subjectAddress)),
                Collections.emptyList());

        return callContractFunctionSync(function);
    }

    @Override
    public SSOSession getSession(String subjectAddress) throws Exception {
        com.keyblock.contract.SSOSession.Session session = this.contract.getSession(subjectAddress).send();

        if(session.subject.equals(ADDRESS_ZERO)){
            return null;
        }
        SSOSession ssoSession = new SSOSession();
        ssoSession.setSessionId(session.sessionId);
        ssoSession.setValidityTime(session.validityTime.longValue());
        ssoSession.setIssuanceDateTimestamp(session.issuanceDate.longValue());
        ssoSession.setIssuerAddress(session.issuer);
        ssoSession.setSignature(new String(session.signature, StandardCharsets.UTF_8));// TODO check cast
        ssoSession.setSubjectAddress(session.subject);

        return ssoSession;
    }

    @Override
    public boolean isSessionActive(String subjectAddress) throws Exception {
        SSOSession session = getSession(subjectAddress);

        long validityTime = session.getValidityTime();
        long issuanceTime = session.getIssuanceDateTimestamp();

        Instant instant = Instant.now();
        return (issuanceTime + validityTime) > instant.getEpochSecond();
    }
}