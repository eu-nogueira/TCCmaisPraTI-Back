package com.example.codeFit_api.controller;

import com.example.codeFit_api.model.Usuario;
import com.example.codeFit_api.repository.UsuarioRepository;
import com.example.codeFit_api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cadastro")
public class CadastroController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @PostMapping("/registro")
    public ResponseEntity<String> registroUsuario(@RequestBody Usuario usuario){
        if(usuarioService.econtrarPeloNomeUsuario(usuario.getNomeUsuario()) != null){
            return ResponseEntity.badRequest().body("Erro! nome do " +
                    "usuário já está em uso");
        }

        usuarioService.salvarUsuario(usuario);
        return ResponseEntity.ok("usuário registrado com sucesso");

    }
}
