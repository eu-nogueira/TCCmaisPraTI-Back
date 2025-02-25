package com.example.codeFit_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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

    @Bean // Define que o método abaixo retornará um bean gerenciado pelo Spring
    public CorsConfigurationSource corsConfigurationSource() { // Método que configura e retorna as definições de CORS
        CorsConfiguration configuration = new CorsConfiguration(); // Cria uma nova instância de CorsConfiguration para definir as configurações de CORS
        configuration.addAllowedOrigin("http://localhost:5173"); // Permite requisições de origem "http://localhost:5173"
        configuration.addAllowedMethod("*"); // Permite todos os métodos HTTP (GET, POST, PUT, DELETE, etc.)
        configuration.addAllowedHeader("*"); // Permite todos os cabeçalhos HTTP
        configuration.setAllowCredentials(true); // Permite o envio de credenciais (cookies, cabeçalhos de autorização, etc.) nas requisições

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // Cria uma nova instância para mapear as configurações de CORS com base em URLs
        source.registerCorsConfiguration("/**", configuration); // Registra as configurações de CORS para todas as rotas da aplicação
        return source; // Retorna a fonte de configuração CORS
    }
}
