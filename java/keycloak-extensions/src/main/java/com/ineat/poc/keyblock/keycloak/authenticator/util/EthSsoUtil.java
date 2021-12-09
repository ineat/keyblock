package com.ineat.poc.keyblock.keycloak.authenticator.util;

import com.keyblock.SSOSessionConnector;
import com.keyblock.model.SSOSession;
import com.keyblock.model.TxReceipt;
import org.keycloak.models.AuthenticatorConfigModel;

import java.util.UUID;

import static com.ineat.poc.keyblock.keycloak.authenticator.AbstractBlockchainAuthenticatorFactory.BLOCKCHAIN_URI;
import static com.ineat.poc.keyblock.keycloak.authenticator.AbstractBlockchainAuthenticatorFactory.IAM_ADDRESS;
import static com.ineat.poc.keyblock.keycloak.authenticator.AbstractBlockchainAuthenticatorFactory.IAM_PRIVATE_KEY;
import static com.ineat.poc.keyblock.keycloak.authenticator.AbstractBlockchainAuthenticatorFactory.SESSION_TTL;
import static com.ineat.poc.keyblock.keycloak.authenticator.AbstractBlockchainAuthenticatorFactory.SMART_CONTACT_ADDRESS;

public abstract class EthSsoUtil {

    public static SSOSession getSsoSession(AuthenticatorConfigModel authenticatorConfig, String ethAddress) throws Exception {
        final SSOSessionConnector session = initEthConnection(authenticatorConfig);
        return session.getSession(ethAddress);
    }

    public static String createSsoSession(AuthenticatorConfigModel authenticatorConfig, String ethAddress) throws Exception {
        long sessionTtl = Long.parseLong(authenticatorConfig.getConfig().getOrDefault(SESSION_TTL, "1800"));
        SSOSession session = new SSOSession();
        session.setSessionId(UUID.randomUUID().toString());
        session.setSubjectAddress(ethAddress);
        session.setValidityTime(sessionTtl);

        final SSOSessionConnector connection = initEthConnection(authenticatorConfig);
        final TxReceipt sessionSync = connection.createSessionSync(session);
        return sessionSync.getBlockHash();
    }

    private static SSOSessionConnector initEthConnection(AuthenticatorConfigModel authenticatorConfig){
        return new SSOSessionConnector(
                authenticatorConfig.getConfig().getOrDefault(BLOCKCHAIN_URI, ""),
                authenticatorConfig.getConfig().getOrDefault(SMART_CONTACT_ADDRESS, ""),
                authenticatorConfig.getConfig().getOrDefault(IAM_ADDRESS, ""),
                authenticatorConfig.getConfig().getOrDefault(IAM_PRIVATE_KEY, "")
        );
    }
}
