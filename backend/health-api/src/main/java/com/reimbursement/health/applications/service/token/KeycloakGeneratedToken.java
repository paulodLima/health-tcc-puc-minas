package com.reimbursement.health.applications.service.token;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

public class KeycloakGeneratedToken {
    @Autowired
    private Environment env;

    public static final String REALM = "health";

    public String generatedToken(String username, String password) {
        var serverUrl = env.getProperty("keycloak-realm.base-url");
        var clientId = env.getProperty("keycloak-realm.client-id");
        var clientSecret = env.getProperty("keycloak-realm.client-secret");
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(REALM)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(username)
                .password(password)
                .grantType(OAuth2Constants.PASSWORD)
                .build();

        AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();
        return tokenResponse.getToken();
    }
}
