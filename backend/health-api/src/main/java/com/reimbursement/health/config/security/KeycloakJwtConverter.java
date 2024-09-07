package com.reimbursement.health.config.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class KeycloakJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    private final ObjectMapper objectMapper;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        var grants = getResourceRoles(jwt).stream().map(SimpleGrantedAuthority::new).toList();
        return new JwtAuthenticationToken(jwt, grants);
    }

    private Set<String> getResourceRoles(Jwt jwt) {
        Set<String> rolesWithPrefix = new HashSet<>();
        Map<String, JsonNode> map = objectMapper.convertValue(jwt.getClaim("resource_access"), new TypeReference<Map<String, JsonNode>>(){});

        if (map.get(clientId) == null) {
            return Set.of();
        }

        map.get(clientId)
                .elements()
                .forEachRemaining(e -> e
                        .elements()
                        .forEachRemaining(r -> rolesWithPrefix.add(createRole(r.asText()))));
        return rolesWithPrefix;
    }

    private String createRole(String... values) {
        StringBuilder role = new StringBuilder("ROLE");
        for (String value : values) {
            role.append("_").append(value.toUpperCase());
        }
        return role.toString();
    }
}
