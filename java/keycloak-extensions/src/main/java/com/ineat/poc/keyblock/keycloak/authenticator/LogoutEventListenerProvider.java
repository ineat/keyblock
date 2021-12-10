/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ineat.poc.keyblock.keycloak.authenticator;

import com.ineat.poc.keyblock.keycloak.authenticator.util.EthSsoUtil;
import org.jboss.logging.Logger;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.UserModel;

import java.util.Optional;
import java.util.Set;

import static com.ineat.poc.keyblock.keycloak.authenticator.BlockchainWalletAuthenticator.BLOCKCHAIN_ADDRESS_CUSTOM_ATTRIBUTE;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class LogoutEventListenerProvider implements EventListenerProvider {

    private static final Logger log = Logger.getLogger(LogoutEventListenerProvider.class);

    private KeycloakSession session;
    private RealmProvider model;
    private Set<EventType> includedEvents;

    public LogoutEventListenerProvider(KeycloakSession session, Set<EventType> includedEvents) {
        this.session = session;
        this.model = session.realms();
        this.includedEvents = includedEvents;
    }

    @Override
    public void onEvent(Event event) {
        if (includedEvents.contains(event.getType())) {
            if (event.getRealmId() != null && event.getUserId() != null) {
                RealmModel realm = model.getRealm(event.getRealmId());
                UserModel user = session.users().getUserById(realm, event.getUserId());

                // we assume that the blockchain SSO execution is present in the browser flow
                final Optional<AuthenticationExecutionModel> first = realm.getAuthenticationExecutionsStream(realm.getBrowserFlow().getId()).filter(authenticationExecutionModel -> authenticationExecutionModel.getAuthenticator().equals(BlockchainSsoAuthenticatorFactory.PROVIDER_ID)).findFirst();
                if (first.isPresent()) {
                    // so we are able to get the execution config to communicate with the ethereum blockchain
                    final AuthenticatorConfigModel ssoConfig = realm.getAuthenticatorConfigById(first.get().getAuthenticatorConfig());

                    if (ssoConfig == null) {
                        log.warn("Unable to find the Ethereum SSO Execution config");
                    } else {
                        if (user != null) {
                            final String userEthAddress = user.getFirstAttribute(BLOCKCHAIN_ADDRESS_CUSTOM_ATTRIBUTE);
                            if (userEthAddress != null) {
                                try {
                                    final String txHash = EthSsoUtil.revoveSsoSession(ssoConfig, userEthAddress);
                                    log.infof("Ethereum Session revoked for user %s. TxHash: %s", user.getUsername(), txHash);
                                } catch (Exception e) {
                                    log.error("Failed to revoke session", e);
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {

    }

    @Override
    public void close() {
    }

}
