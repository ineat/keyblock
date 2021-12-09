package com.ineat.poc.keyblock.keycloak.authenticator;

import com.ineat.poc.keyblock.keycloak.authenticator.util.EthSsoUtil;
import com.keyblock.model.SSOSession;
import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.utils.StringUtil;

import javax.ws.rs.core.MultivaluedMap;

import static com.ineat.poc.keyblock.keycloak.authenticator.BlockchainWalletAuthenticator.BLOCKCHAIN_ADDRESS_CUSTOM_ATTRIBUTE;
import static com.ineat.poc.keyblock.keycloak.authenticator.BlockchainWalletAuthenticator.BLOCKCHAIN_PAGE;

public class BlockchainSsoAuthenticator implements Authenticator {
    public static final BlockchainSsoAuthenticator SINGLETON = new BlockchainSsoAuthenticator();

    protected static final Logger logger = Logger.getLogger(BlockchainSsoAuthenticator.class);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        context.challenge(context.form().createForm(BLOCKCHAIN_PAGE));
//        AuthenticationManager.AuthResult authResult = authenticateIdentityBlockchain(context.getAuthenticatorConfig(), context.getSession(),
//                context.getRealm(), true);
//        if (authResult == null) {
//            context.attempted();
//        } else {
//            AuthenticationSessionModel clientSession = context.getAuthenticationSession();
//            LoginProtocol protocol = context.getSession().getProvider(LoginProtocol.class, clientSession.getProtocol());
//
//            // Cookie re-authentication is skipped if re-authentication is required
//            if (protocol.requireReauthentication(authResult.getSession(), clientSession)) {
//                context.attempted();
//            } else {
//                context.getAuthenticationSession().setAuthNote(AuthenticationManager.SSO_AUTH, "true");
//
//                context.setUser(authResult.getUser());
//                context.attachUserSession(authResult.getSession());
//                context.success();
//            }
//        }

    }

    private SSOSession authenticateIdentityBlockchain(AuthenticatorConfigModel authenticatorConfig, String ethAddress) {

        //TODO how to deal with connection issues ?
        //FIXME move this init

        final SSOSession ethSession;
        try {
            ethSession = EthSsoUtil.getSsoSession(authenticatorConfig, ethAddress);
        } catch (Exception e) {
            logger.errorf("Unable to get Ethereum SSO Session: %s", e.getMessage());
            return null;
        }

        if (ethSession == null) {
            logger.info("Could not authenticate thanks to the Blockchain");
            return null;
        }

        return ethSession;
//        ethSession.


    }

    @Override
    public void action(AuthenticationFlowContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();

        if (formData.containsKey("cancel")) {
            context.cancelLogin();
            return;
        }
        String ethAddress = formData.getFirst("blockchain-address");
        if(StringUtil.isBlank(ethAddress)){
            logger.info("Unable to do sso because of empty eth address");
            context.attempted();

            return;
        }

        SSOSession ethSession = authenticateIdentityBlockchain(context.getAuthenticatorConfig(), ethAddress);
        if(ethSession == null){
            logger.infof("Unable to do sso because of no decentralized session found for eth address %s", ethAddress);
            context.attempted();

            return;
        }else{
            // TODO check TTL
            UserModel userModel = context.getSession().users().searchForUserByUserAttributeStream(context.getRealm(), BLOCKCHAIN_ADDRESS_CUSTOM_ATTRIBUTE, ethSession.getSubjectAddress()).findFirst().orElse(null);
            if (userModel == null) {
                logger.infof("Unable to find Keycloak user with eth address %s", ethAddress);
                context.attempted();
                return;
            }

            context.setUser(userModel);

            context.success();

            return;
        }
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        if (user != null) {
            final String userBlockchainAddress = user.getAttributeStream(BLOCKCHAIN_ADDRESS_CUSTOM_ATTRIBUTE).findFirst().orElse("");
            return StringUtil.isNotBlank(userBlockchainAddress);
        }

        return false;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {

    }

    @Override
    public void close() {

    }
}
