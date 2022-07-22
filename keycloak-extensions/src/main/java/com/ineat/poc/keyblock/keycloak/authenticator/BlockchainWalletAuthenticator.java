package com.ineat.poc.keyblock.keycloak.authenticator;

import com.ineat.poc.keyblock.keycloak.authenticator.util.EthRecoverUtil;
import com.ineat.poc.keyblock.keycloak.authenticator.util.EthSsoUtil;
import org.jboss.logging.Logger;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.utils.StringUtil;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.security.SignatureException;

import static com.ineat.poc.keyblock.keycloak.authenticator.BlockchainWalletAuthenticatorFactory.CREATE_ETH_SSO_SESSION;
import static java.lang.Boolean.parseBoolean;

public class BlockchainWalletAuthenticator implements Authenticator {
    public static final BlockchainWalletAuthenticator SINGLETON = new BlockchainWalletAuthenticator();
    public static final String BLOCKCHAIN_ADDRESS_CUSTOM_ATTRIBUTE = "blockchain.address";
    public static final String BLOCKCHAIN_PAGE = "blockchain-wallet-page.ftl";
    private static Logger logger = Logger.getLogger(BlockchainWalletAuthenticator.class);

    public static String getConfigString(AuthenticatorConfigModel config, String configName) {
        String value = null;
        if (config.getConfig() != null) {
            // Get value
            value = config.getConfig().get(configName);
        }

        return value;
    }

    public void authenticate(AuthenticationFlowContext context) {

        String error = "";
        Response challengeResponse = null;
        if (StringUtil.isNotBlank(error)) {
            challengeResponse = this.challenge(context, error);
        } else {
            challengeResponse = this.challenge(context, new MultivaluedMapImpl());
        }

        context.challenge(challengeResponse);
    }

    protected Response challenge(AuthenticationFlowContext context, MultivaluedMap<String, String> formData) {
        LoginFormsProvider forms = context.form();
        if (formData.size() > 0) {
            forms.setFormData(formData);
        }
        forms.setAttribute("isAuthentication", true);
        return forms.createForm(BLOCKCHAIN_PAGE);
    }

    protected Response challenge(AuthenticationFlowContext context, String error, Object... parameters) {
        LoginFormsProvider form = context.form();
        if (error != null) {
            form.setError(error, parameters);
        }
        form.setAttribute("isAuthentication", true);
        return form.createForm(BLOCKCHAIN_PAGE);
    }

    public void action(AuthenticationFlowContext context) {
        boolean isEthSsoEnabled = parseBoolean(getConfigString(context.getAuthenticatorConfig(), CREATE_ETH_SSO_SESSION));
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();

        if (formData.containsKey("cancel")) {
            context.cancelLogin();
            return;
        }

        final String ethData = formData.getFirst("blockchain-data");
        final String ethSig = formData.getFirst("blockchain-sig");
        String ethAddress = formData.getFirst("blockchain-address");


        UserModel userModel = context.getUser();
        if (userModel != null) {
            String usrEthAddressId = userModel.getFirstAttribute(BLOCKCHAIN_ADDRESS_CUSTOM_ATTRIBUTE);
            if (StringUtil.isBlank(usrEthAddressId) || !usrEthAddressId.equalsIgnoreCase(ethAddress)) {
                Response challengeResponse = this.challenge(context, "unableToRecover", "addresses does not match!");
                context.challenge(challengeResponse);
                context.attempted();

                return;
            }
            // doing recover based on the session user addressId
            ethAddress = usrEthAddressId;
        }

        boolean recover = false;
        try {
            recover = EthRecoverUtil.recover(ethData, ethSig, ethAddress);
        } catch (SignatureException e) {
            Response challengeResponse = this.challenge(context, "unableToRecover");
            context.challenge(challengeResponse);
            context.attempted();

            return;
        }

        if (recover) {
            // single login
            if (userModel == null) {
                userModel = context.getSession().users().searchForUserByUserAttributeStream(context.getRealm(), BLOCKCHAIN_ADDRESS_CUSTOM_ATTRIBUTE, ethAddress).findFirst().orElse(null);
                if (userModel == null) {
                    context.challenge(this.challenge(context, "userNotFound"));
                    return;
                }

                if(isEthSsoEnabled){
                    // creation of an Ethereum block
                    try {
                        final String txHash = EthSsoUtil.createSsoSession(context.getAuthenticatorConfig(), ethAddress);
                        context.getAuthenticationSession().setAuthNote("SSO_ETH_HASH", txHash);
                        logger.infof("Ethereum sso session successful created with hash %s", txHash);
                    } catch (Exception e) {
                        logger.errorf("Unable to create the Ethereum SSO session due to the following error: %s", e.getMessage());
                    }
                }
                context.setUser(userModel);
            }
            context.success();
        } else {
            Response challengeResponse = this.challenge(context, "authenticationKo");
            context.challenge(challengeResponse);
            context.attempted();
        }

    }

    public boolean requiresUser() {
        return false;
    }

    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        if (user != null) {
            final String userBlockchainAddress = user.getAttributeStream(BLOCKCHAIN_ADDRESS_CUSTOM_ATTRIBUTE).findFirst().orElse("");
            return StringUtil.isNotBlank(userBlockchainAddress);
        }

        return false;
    }

    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        //TODO do something here to force the user to set his blockchain address
    }

    public void close() {

    }

    class SigUtilData {
        public String data;
        public String sig;
    }
}
