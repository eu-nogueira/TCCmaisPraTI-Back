package com.example.codeFit_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
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
                        .requestMatchers("/login/**").permitAll()
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

    // DEFINE COMO VAMOS AUTENTICAR UM USUÁRIO
    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService, // Serviço para carregar os detalhes do usuário do banco de dados.
            PasswordEncoder passwordEncoder // Codificador de senha utilizado para validar senhas no login.
    ) throws Exception {
        // Cria um provedor de autenticação que utiliza um banco de dados para validar usuários.
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // Define o serviço que carrega os detalhes do usuário.
        provider.setPasswordEncoder(passwordEncoder); // Define o codificador de senha (Argon2 no caso).

        // Retorna um AuthenticationManager que utiliza o provedor de autenticação configurado.
        return new ProviderManager(provider);
    }
}
