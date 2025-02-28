package com.example.codeFit_api.controller;


import com.example.codeFit_api.model.Usuario;
import com.example.codeFit_api.repository.UsuarioRepository;
import com.example.codeFit_api.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JwtUtil jwtUtil;

    public LoginController(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody Usuario usuario){

        try{
            // Exibe no console os dados do usuário que está tentando se autenticar (usado para depuração).
            System.out.println("Tentando autenticar: " + usuario.getNomeUsuario());
            System.out.println("Senha fornecida: " + usuario.getSenha());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario.getNomeUsuario(), usuario.getSenha())
            );

            // Exibe no console que o usuário foi autenticado com sucesso.
            System.out.println("Usuário autenticado com sucesso: " + authentication.getName());
            // Gera um token JWT para o usuário autenticado.
            String token = jwtUtil.gerarToken(authentication.getName());
            // Retorna o token no corpo da resposta.
            return ResponseEntity.ok(token);


        }catch (Exception e){
            // Exibe o erro no console e retorna uma resposta de erro 401.
            System.out.println("Erro ao autenticar: " + e.getMessage());
            return ResponseEntity.status(401).body("Usuário inexistente ou senha inválida");





        }

    }
}
