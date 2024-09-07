package com.reimbursement.health.applications.service;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KeycloackService {

    @Value("${keycloak.base-url}")
    private String baseUrl;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.username}")
    private String username;

    @Value("${keycloak.password}")
    private String password;

    public String teste() {
        return "Hello from Quarkus REST";
    }

    public Keycloak login() {
        return KeycloakBuilder.builder()
                .serverUrl(baseUrl)
                .realm("master")
                .clientId(clientId)
                .username(username)
                .password(password)
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }

    public List<UserRepresentation> getUsers(String realmName) {
        Keycloak keycloak = login();

        return keycloak.realm(realmName).users().list();
    }

    public UserRepresentation getUser(String realmName, UUID id) {
        Keycloak keycloak = login();

        return keycloak.realm(realmName).users().get(id.toString()).toRepresentation();
    }

    public void editUser(UUID userId, String realmName, String firstName, String lastName, String email) {
        Keycloak keycloak = login();

        RealmResource realmResource = keycloak.realm(realmName);
        UsersResource usersResource = realmResource.users();

        UserResource userResource = usersResource.get(userId.toString());

        UserRepresentation user = userResource.toRepresentation();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        userResource.update(user);
    }

    public UUID createUser(String realmName, String username, String firstName, String lastName, String email) {
        Keycloak keycloak = login();

        RealmResource realmResource = keycloak.realm(realmName);
        UsersResource usersResource = realmResource.users();

        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(username);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setEnabled(true);
        newUser.setEmailVerified(true);

        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false); //valid
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue("123456");

        newUser.setCredentials(Collections.singletonList(passwordCredentials));

        // created user in Keycloak
        var response = usersResource.create(newUser);

        if (response.getStatus() == 201) {
            System.out.println("user created successfully!");
        } else {
            System.out.println("error creating user: " + response.getStatus());
        }

        response.close();
        return UUID.fromString(response.getLocation().getPath().substring(response.getLocation().getPath().lastIndexOf('/') + 1));
    }

    public void updatePasswordUser(UUID id, String realm, String password) {
        Keycloak keycloak = login();

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        UserResource userResource = usersResource.get(id.toString());

        UserRepresentation user = userResource.toRepresentation();

        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false); //valid
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);

        user.setCredentials(Collections.singletonList(passwordCredentials));
        userResource.update(user);
    }
}
