package com.ineat.poc.keyblock.keycloak.authenticator;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.authentication.ConfigurableAuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.ArrayList;
import java.util.List;

public class BlockchainWalletAuthenticatorFactory implements AuthenticatorFactory, ConfigurableAuthenticatorFactory {

    public static final String PROVIDER_ID = "blockchain-wallet-authenticator";

    public static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.ALTERNATIVE,
            AuthenticationExecutionModel.Requirement.CONDITIONAL,
            AuthenticationExecutionModel.Requirement.DISABLED
    };

    private static final List<ProviderConfigProperty> CONFIG_PROPERTIES = new ArrayList<ProviderConfigProperty>();

    public static final String METAMASK_TYPE = "METAMASK_TYPE";

    static {
        CONFIG_PROPERTIES.add(
                new ProviderConfigProperty(
                        "walletType",
                        "Wallet type to use for user authentication",
                        "",
                        ProviderConfigProperty.LIST_TYPE,
                        METAMASK_TYPE,
                        false
                ));

        CONFIG_PROPERTIES.add(
                new ProviderConfigProperty(
                        "signatureMessage",
                        "Signature message to display.",
                        "Which message to display when an user signature is requested?",
                        ProviderConfigProperty.STRING_TYPE,
                        "Veuillez signer pour vous authentifier",
                        false
                ));
    }

    public String getId() {
        return PROVIDER_ID;
    }

    public String getDisplayType() {
        return "Wallet Blockchain Authenticator";
    }

    public String getHelpText() {
        return "A Wallet based blockchain Authenticator ";
    }

    public String getReferenceCategory() {
        return "Blockchain";
    }

    public boolean isConfigurable() {
        return true;
    }

    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    public boolean isUserSetupAllowed() {
        return false;
    }

    public List<ProviderConfigProperty> getConfigProperties() {
        return CONFIG_PROPERTIES;
    }

    public Authenticator create(KeycloakSession keycloakSession) {
        return BlockchainWalletAuthenticator.SINGLETON;
    }

    public void init(Config.Scope scope) {
        // NOOP
    }

    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        // NOOP
    }

    public void close() {
        // NOOP
    }


}
