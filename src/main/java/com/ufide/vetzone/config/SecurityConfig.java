package com.ufide.vetzone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ufide.vetzone.service.UsuarioDetailsService;

@Configuration
public class SecurityConfig {

    private final UsuarioDetailsService usuarioDetailsService;

    public SecurityConfig(UsuarioDetailsService usuarioDetailsService) {
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/usuarios")
            )

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                    "/login",
                    "/access-denied",
                    "/css/**",
                    "/js/**",
                    "/images/**"
                ).permitAll()

                // Todo el módulo de usuarios es exclusivo de ADMIN
                .requestMatchers("/usuarios/**")
                    .hasRole("ADMIN")

                // Cualquier otra ruta requiere autenticación
                .anyRequest().authenticated()
            )

            .userDetailsService(usuarioDetailsService)

            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .permitAll()
            )

            .exceptionHandling(exception -> exception
                .accessDeniedPage("/access-denied")
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }
}