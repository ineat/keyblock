package com.ineat.poc.keyblock.keycloak.authenticator;

import org.keycloak.provider.ProviderConfigProperty;

import java.util.ArrayList;
import java.util.List;


public class AbstractBlockchainAuthenticatorFactory {
    public static final String BLOCKCHAIN_URI = "blockchain-uri";
    public static final String SMART_CONTACT_ADDRESS = "smartContactAddress";
    public static final String IAM_ADDRESS = "iamAddress";
    public static final String IAM_PRIVATE_KEY = "iamPrivateKey";
    public static final String SESSION_TTL = "sessionTtl";

    protected static final List<ProviderConfigProperty> SHARED_CONFIG_PROPERTIES = new ArrayList<>();

    static {
        SHARED_CONFIG_PROPERTIES.add(
                new ProviderConfigProperty(
                        BLOCKCHAIN_URI,
                        "The blockchain URI",
                        "The blockchain URI to connect to (Infura for example)",
                        ProviderConfigProperty.STRING_TYPE,
                        "https://api.avax-test.network/ext/bc/C/rpc"
                ));

        SHARED_CONFIG_PROPERTIES.add(
                new ProviderConfigProperty(
                        SMART_CONTACT_ADDRESS,
                        "SSO Smart contract address.",
                        "The address of the SSO Smart Contract",
                        ProviderConfigProperty.STRING_TYPE,
                        "0x2166338F208E97003e3844E5A0C4DB11E7509383",
                        false
                ));

        SHARED_CONFIG_PROPERTIES.add(
                new ProviderConfigProperty(
                        IAM_ADDRESS,
                        "IAM Ethereum Address",
                        "",
                        ProviderConfigProperty.STRING_TYPE,
                        "0x41f6B225846863E3C037e92F229cD40f5d575258",
                        false
                ));
        SHARED_CONFIG_PROPERTIES.add(
                new ProviderConfigProperty(
                        IAM_PRIVATE_KEY,
                        "IAM Private key",
                        "IAM private key used to sign messages",
                        ProviderConfigProperty.PASSWORD,
                        "85d4fc54c9c6de275f5b0ac1a975657ed95d3959cdb97edc9da953bf1a75c723",
                        true
                ));

        SHARED_CONFIG_PROPERTIES.add(
                new ProviderConfigProperty(
                        SESSION_TTL,
                        "Ethereum Session TTL in seconds",
                        "Time to live (in seconde) before expiration",
                        ProviderConfigProperty.STRING_TYPE,
                        "1800"
                ));
    }

}
