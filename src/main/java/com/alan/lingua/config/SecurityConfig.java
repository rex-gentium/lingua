package com.alan.lingua.config;

import com.alan.lingua.security.JwtAuthenticationManager;
import com.alan.lingua.security.JwtSecurityContextRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         JwtAuthenticationManager jwtAuthenticationManager,
                                                         JwtSecurityContextRepository jwtSecurityContextRepository) {
        return http.exceptionHandling()
                .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() ->
                        swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
                .accessDeniedHandler((swe, e) -> Mono.fromRunnable(() ->
                        swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
                .and()
                .authenticationManager(jwtAuthenticationManager)
                .securityContextRepository(jwtSecurityContextRepository)
                .authorizeExchange()
                .pathMatchers(HttpMethod.POST, "/auth/login", "/api/person").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/dictionary/**").permitAll()
                .pathMatchers(AUTH_WHITELIST).permitAll()
                .pathMatchers("/api/**").authenticated()
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
