package io.soffa.platform.gateways.security;

import io.soffa.platform.core.security.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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


    private Environment environment;

    private final ReactiveAuthenticationManager authenticationManager;

    private final ServerSecurityContextRepository securityContextRepository;

    public SecurityBeansFactory(Environment environment, ReactiveAuthenticationManager authenticationManager, ServerSecurityContextRepository securityContextRepository) {
        this.environment = environment;
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        boolean isEnabled = Boolean.parseBoolean(environment.getProperty("application.security.enabled", "false"));

        if (isEnabled) {

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
        } else {
            return http.cors().disable()
                .exceptionHandling()
                .and()
                .csrf().disable()
                .authorizeExchange().pathMatchers("/**").permitAll()
                .and()
                .build();
        }
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
