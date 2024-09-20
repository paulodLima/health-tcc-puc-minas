package com.reimbursement.health.applications.service;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class KeycloackService {

    @Value("${keycloak.base-url}")
    private String baseUrl;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.username}")
    private String username;

    @Value("${keycloak.password}")
    private String password;

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

    public void deleteUser(String realmName, UUID id) {
        Keycloak keycloak = login();
        try {
            keycloak.realm(realmName).users().delete(id.toString());
            keycloak.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            keycloak.close();
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    public UserRepresentation getUser(String realmName, UUID id) {
        Keycloak keycloak = login();

        UserResource userResource = keycloak.realm(realmName).users().get(id.toString());

        UserRepresentation user = userResource.toRepresentation();
        List<RoleRepresentation> realmRoles = userResource.roles().realmLevel().listAll();

        Set<String> desiredRoles = Set.of("manager", "user", "admin");

        List<String> filteredRoleNames = realmRoles.stream()
                .map(RoleRepresentation::getName)
                .filter(desiredRoles::contains)
                .collect(Collectors.toList());

        user.setRealmRoles(filteredRoleNames);

        return user;
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

    public UUID createUser(String realmName, String username, String firstName, String lastName, String email, String password,String roleUser) {
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
        passwordCredentials.setValue(password);

        newUser.setCredentials(Collections.singletonList(passwordCredentials));

        var response = usersResource.create(newUser);
        var uuid = UUID.fromString(response.getLocation().getPath().substring(response.getLocation().getPath().lastIndexOf('/') + 1));

        List<RoleRepresentation> roles = keycloak.realm(realmName).roles().list();

        roles.stream()
                .filter(r -> r.getName().equals(roleUser))
                .findFirst().ifPresent(role -> keycloak.realm(realmName).users().get(uuid.toString()).roles().realmLevel().add(Collections.singletonList(role)));

        ClientRepresentation client = keycloak.realm(realmName).clients().findByClientId("health-api").stream().findAny().orElse(null);

        if (client != null) {
            List<RoleRepresentation> clientRoles = keycloak.realm(realmName).clients().get(client.getId()).roles().list();

            clientRoles.stream()
                    .filter(r -> r.getName().equals(roleUser))
                    .findFirst().ifPresent(role -> keycloak.realm(realmName).users().get(uuid.toString())
                            .roles().clientLevel(client.getId())
                            .add(Collections.singletonList(role)));

        }

        if (response.getStatus() == 201) {
            System.out.println("user created successfully!");
        } else {
            System.out.println("error creating user: " + response.getStatus());
        }

        response.close();
        return uuid;
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
