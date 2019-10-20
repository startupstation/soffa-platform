package io.soffa.adapter.security;

import io.soffa.core.security.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import reactor.core.publisher.Mono;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
public class SecurityBeansFactory {

    @Autowired
    private ReactiveAuthenticationManager authenticationManager;

    @Autowired
    private ServerSecurityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.cors().disable()
            .exceptionHandling()
            .authenticationEntryPoint((exchange, e) -> Mono.from(s -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
            .accessDeniedHandler((exchange, denied) -> Mono.from(s -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
            .and()
            .csrf().disable()
            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository)
            .authorizeExchange()
            .pathMatchers("/api/**").authenticated()
            .anyExchange().permitAll()
            .and()
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return new PasswordEncoder() {

            @Override
            public String encode(CharSequence rawPassword) {
                return encoder.encode(rawPassword);
            }

            @Override
            public Boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encoder.matches(rawPassword, encodedPassword);
            }

        };
    }

}
