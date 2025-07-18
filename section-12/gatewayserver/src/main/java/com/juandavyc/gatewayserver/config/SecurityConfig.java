package com.juandavyc.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {

        serverHttpSecurity
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.GET).permitAll()
//                        .pathMatchers("/bank/accounts/**").authenticated()
//                        .pathMatchers("/bank/loans/**").authenticated()
//                        .pathMatchers("/bank/cards/**").authenticated())
                        .pathMatchers("/bank/accounts/**").hasRole("ACCOUNTS")
                        .pathMatchers("/bank/loans/**").hasRole("LOANS")
                        .pathMatchers("/bank/cards/**").hasRole("CARDS"))
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(gratedAuthoritiesExtractor()))
                ).csrf(csrfSpec -> csrfSpec.disable());

        return serverHttpSecurity.build();
    }

    private Converter<Jwt,Mono<AbstractAuthenticationToken>> gratedAuthoritiesExtractor(){
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}
