package io.soffa.platform.gateways.security;

import io.soffa.platform.core.commons.StringUtil;
import io.soffa.platform.core.security.TokenProvider;
import io.soffa.platform.core.security.model.DecodedToken;
import io.soffa.platform.core.security.model.UserPrincipal;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;


public class ServerHttpBearerAuthenticationConverter implements ServerAuthenticationConverter {

    private static final String BEARER = "Bearer ";

    private final TokenProvider tokenProvider;

    private static final Authentication ANONYMOUS = new UsernamePasswordAuthenticationToken(
        "guest",
        "guest",
        Collections.singletonList(new SimpleGrantedAuthority("guest"))
    );

    public ServerHttpBearerAuthenticationConverter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange serverWebExchange) {
        return Mono.justOrEmpty(serverWebExchange.getRequest())
            .flatMap(request -> {
                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return Mono.just(ANONYMOUS);
                }
                String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (StringUtil.isNullOrEmpty(authHeader) || !authHeader.startsWith(BEARER)) {
                    return Mono.just(ANONYMOUS);
                }
                return tokenProvider.decode(authHeader.substring(BEARER.length()))
                    .switchIfEmpty(Mono.just(DecodedToken.ANONYMOUS))
                    .map(decodedToken -> {

                        if (decodedToken == DecodedToken.ANONYMOUS) {
                            return ANONYMOUS;
                        }

                        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                        authorities.add(new SimpleGrantedAuthority("user"));
                        if (decodedToken.getRoles() != null) {
                            for (String role : decodedToken.getRoles()) {
                                authorities.add(new SimpleGrantedAuthority(role));
                            }
                        }
                        UserPrincipal principal = new UserPrincipal(decodedToken.getUsername(), decodedToken.getEmail(), decodedToken.getRoles());
                        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
                    });
            });
    }

}
