package com.juandavyc.gatewayserver.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Jwt jwt = (Jwt) source;
        Map<String, Object> realmClaims = jwt.getClaim("realm_access");
        if (realmClaims == null || realmClaims.isEmpty() ) {
            return List.of();
        }

        List<String> roles = (List<String>) realmClaims.get("roles");
        if (roles == null || roles.isEmpty() ) {
            return List.of();
        }

        Collection<GrantedAuthority> authorities = roles.stream()
                .map(role->new SimpleGrantedAuthority("ROLE_"+role))
                .collect(Collectors.toList());

        return authorities;
    }
}
