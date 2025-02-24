package com.example.codeFit_api.service;


import com.example.codeFit_api.model.Usuario;
import com.example.codeFit_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario salvarUsuario(Usuario usuario){
        String senhaComHash =
                passwordEncoder.encode(usuario.getSenha());
        // Define a senha criptografada no objeto usuário antes de salvar no banco de dados.
        usuario.setSenha(senhaComHash);

        // Salva o usuário no banco de dados e retorna o objeto salvo.
        return usuarioRepository.save(usuario);
    }
    public Usuario econtrarPeloNomeUsuario(String nomeUsuario){
        // Usa o repositório para buscar o usuário pelo nome de usuário.
        // Retorna um Optional contendo o usuário, ou null se não encontrado.
        return usuarioRepository.findByNomeUsuario(nomeUsuario).orElse(null);

    }

}
