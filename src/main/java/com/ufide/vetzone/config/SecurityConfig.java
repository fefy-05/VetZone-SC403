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

            // CSRF
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/usuarios")
            )

            // PERMISOS
            .authorizeHttpRequests(auth -> auth

                // Páginas públicas
                .requestMatchers(
                    "/login",
                    "/usuarios/nuevo",
                    "/css/**",
                    "/js/**",
                    "/images/**"
                ).permitAll()

                // Solo ADMIN puede administrar usuarios
                .requestMatchers("/usuarios/**")
                    .hasRole("ADMIN")

                // Todo lo demás requiere iniciar sesión
                .anyRequest().authenticated()
            )

            // Servicio que busca los usuarios en MySQL
            .userDetailsService(usuarioDetailsService)

            // LOGIN
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")

                // Después del login TODOS van al inicio
                .defaultSuccessUrl("/", true)

                .failureUrl("/login?error")
                .permitAll()
            )

            // LOGOUT
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

