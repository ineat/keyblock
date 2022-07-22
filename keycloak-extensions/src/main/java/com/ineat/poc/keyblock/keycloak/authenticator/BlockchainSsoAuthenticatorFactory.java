package com.ineat.poc.keyblock.keycloak.authenticator;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.ArrayList;
import java.util.List;


public class BlockchainSsoAuthenticatorFactory extends AbstractBlockchainAuthenticatorFactory implements AuthenticatorFactory {
    public static final String PROVIDER_ID = "blockchain-sso-authenticator";
    public static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
//            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.ALTERNATIVE,
//            AuthenticationExecutionModel.Requirement.CONDITIONAL,
            AuthenticationExecutionModel.Requirement.DISABLED
    };
    protected static final List<ProviderConfigProperty> CONFIG_PROPERTIES = new ArrayList<ProviderConfigProperty>();

    static {
        CONFIG_PROPERTIES.addAll(SHARED_CONFIG_PROPERTIES);
    }

    public String getId() {
        return PROVIDER_ID;
    }

    public String getDisplayType() {
        return "Blockchain SSO Authenticator";
    }

    public String getHelpText() {
        return "A Blockchain silent Authenticator that check if a session block is present in the blockchain in order to do SSO";
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

    public Authenticator create(KeycloakSession keycloakSession) {
        return BlockchainSsoAuthenticator.SINGLETON;
    }

    public List<ProviderConfigProperty> getConfigProperties() {
        return CONFIG_PROPERTIES;
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
