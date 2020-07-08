package io.soffa.platform.gateways.security;

import io.soffa.platform.core.security.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
@EnableReactiveMethodSecurity
public class SecurityBeansFactory {

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, TokenProvider tokenProvider) {

        http
            //.exceptionHandling()
            //.authenticationEntryPoint((exchange, e) -> Mono.from(s -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
            //.accessDeniedHandler((exchange, denied) -> Mono.from(s -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
            //.and()
            .csrf().disable()
            //.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .authorizeExchange()
            .pathMatchers(HttpMethod.OPTIONS).permitAll()
            .pathMatchers("/**").permitAll()
            .and()
            .addFilterAt(bearerAuthenticationFilter(tokenProvider), SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }

    private AuthenticationWebFilter bearerAuthenticationFilter(TokenProvider tokenProvider){
        AuthenticationWebFilter bearerAuthenticationFilter;
        ReactiveAuthenticationManager authManager = new BearerTokenReactiveAuthenticationManager();
        bearerAuthenticationFilter = new AuthenticationWebFilter(authManager);
        bearerAuthenticationFilter.setServerAuthenticationConverter(new ServerHttpBearerAuthenticationConverter(tokenProvider));
        bearerAuthenticationFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/**"));
        return bearerAuthenticationFilter;
    }

    /*
    private final ReactiveAuthenticationManager authenticationManager;
    private final ServerSecurityContextRepository securityContextRepository;

    public SecurityBeansFactory(ReactiveAuthenticationManager authenticationManager, ServerSecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http.cors().disable()
            .exceptionHandling()
            .authenticationEntryPoint((exchange, e) -> Mono.from(s -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
            .accessDeniedHandler((exchange, denied) -> Mono.from(s -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
            .and()
            .csrf().disable()
            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository)
            .authorizeExchange()
            .pathMatchers(HttpMethod.OPTIONS).permitAll()
            .pathMatchers("/actuator/health").permitAll()
            .pathMatchers("/actuator/info").permitAll()
            .anyExchange().authenticated().and()
            .build();
    }

    /*
        boolean isEnabled = Boolean.parseBoolean(environment.getProperty("application.security.enabled", "false"));
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

    /*
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
    */
}
