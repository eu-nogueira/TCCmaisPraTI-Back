package com.example.codeFit_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class securityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // utiliza BCrypt para codificar as senhas
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desabilita a proteção contra CSRF (necessário avaliar se a API realmente não precisa dessa proteção).
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/cadastro").permitAll() //
                        // Permite acesso irrestrito às rotas que começam com "/auth/" (exemplo: login, registro).
                        .requestMatchers("/api/protected").hasAuthority("ROLE_USER") // Restringe o acesso a "/api/protected" apenas para usuários com a role "ROLE_USER".
                        .anyRequest().authenticated() // Exige autenticação para qualquer outra requisição não especificada acima.
                )
                .build(); // Constrói e retorna a configuração de segurança definida.
    }
}
