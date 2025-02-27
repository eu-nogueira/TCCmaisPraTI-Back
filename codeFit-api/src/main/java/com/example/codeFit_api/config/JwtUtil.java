package com.example.codeFit_api.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public String gerarToken(String nomeUsuario){
        var token = Jwts.builder()
                .setSubject(nomeUsuario) // Define o "assunto" (usuário) do token.
                .setIssuedAt(new Date()) // Define a data e hora de emissão do token.
                .setExpiration((new Date(System.currentTimeMillis() + expirationTime))) // Define a data de expiração do token.
                .signWith(SignatureAlgorithm.HS256, secret) // Assina o token com a chave privada usando RSA
                .compact(); // Constrói o token JWT.


        // Exibe o token gerado no console (para depuração).
        System.out.println("Token gerado: " + token);

        // Retorna o token JWT gerado.
        return token;
    }
    // Método para extrair o nome de usuário (subject) de um token JWT.
    public String obterNomeUsuarioDoToken(String token) {
        try {
            // Verifica se o token está no formato correto usando uma expressão regular.
            if (!token.matches("^[A-Za-z0-9-_\\.]+\\.[A-Za-z0-9-_\\.]+\\.[A-Za-z0-9-_\\.]+$")) {
                System.out.println("Token JWT malformatado: " + token);
                throw new IllegalArgumentException("Token JWT malformatado.");
            }

            // Extrai o nome de usuário da claim "subject".
            return extractClaim(token, Claims::getSubject);
        } catch (Exception e) {
            System.out.println("Erro ao extrair o username do token: " + e.getMessage());
            throw e; // Propaga a exceção após registrar o erro.
        }
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date()); // Compara a data de expiração com a data atual.
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);  // Usa a função Claims::getSubject para obter o "subject".
    }



    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration); // Usa a função Claims::getExpiration para obter a data de expiração
    }

    public  <T> T extractClaim(String token , Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token); // Obtém todas as claims do token.
        return claimsResolver.apply(claims); // Aplica a função fornecida para extrair a claim desejada.
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token) // Analisa o token e retorna suas informações.
                .getBody(); // Obtém o corpo do token (as claims).
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        try{
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch(Exception error){
            return false;
        }

    }

}
