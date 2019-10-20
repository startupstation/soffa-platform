package io.soffa.adapter.security;

import io.soffa.core.security.TokenProvider;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private TokenProvider tokenProvider;

    public AuthenticationManager(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username = tokenProvider.getUsernameFromToken(authToken);
        if (username != null && !tokenProvider.isTokenExpired(authToken)) {
            // val claims = tokenProvider.getAllClaimsFromToken(authToken)
            // val roles = claims.get(AUTHORITIES_KEY, List<*>::class.java)
            // val authorities = roles.stream().map({ role -> SimpleGrantedAuthority(role) }).collect(Collectors.toList())
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
            return Mono.just(auth);
        } else {
            return Mono.empty();
        }
    }
}
