package org.lessons.java.spring_pizzeria_crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                // Definiamo chi può fare cosa usando le lambda
                .requestMatchers("/pizze/create", "/pizze/edit/**", "/pizze/delete/**").hasAuthority("ADMIN") // Solo
                                                                                                              // ADMIN
                .requestMatchers("/pizze", "/pizze/**").hasAnyAuthority("USER", "ADMIN") // Entrambi
                .requestMatchers("/**").permitAll() // Tutto il resto (CSS, Home) è libero
        )
                .formLogin(form -> form.permitAll()) // Abilita il form di login per tutti
                .logout(logout -> logout.permitAll()); // Abilita il logout per tutti

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usa il sistema di default per criptare le password (BCrypt)
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(null);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}