package com.example.codeFit_api.service;

import com.example.codeFit_api.model.CustomUserDetails;
import com.example.codeFit_api.model.Usuario;
import com.example.codeFit_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    // Injeta automaticamente o repositório de usuários para buscar dados do banco de dados.
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Sobrescreve o método loadUserByUsername da interface UserDetailsService.
    // Este método é chamado pelo Spring Security durante a autenticação para carregar os dados do usuário.
    @Override
    public UserDetails loadUserByUsername(String nomeUsuario) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados pelo nome de usuário.
        // Caso o usuário não seja encontrado, lança uma exceção UsernameNotFoundException.

        Usuario user = usuarioRepository.findByNomeUsuario(nomeUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + nomeUsuario));

        // Imprime informações do usuário no console (usado para depuração).
        System.out.println("Usuário carregado: " + user.getNomeUsuario());
        System.out.println("Senha carregada: " + user.getSenha());
        System.out.println("Roles atribuídas: " + List.of(new SimpleGrantedAuthority("ROLE_USER")));


        // Retorna uma instância de CustomUserDetails com as informações do usuário autenticado.
        return new CustomUserDetails(
                user.getNomeUsuario(), // Nome de usuário.
                user.getSenha(), // Senha do usuário (criptografada).
                List.of(new SimpleGrantedAuthority("ROLE_USER")) // Lista de permissões (neste caso, apenas "ROLE_USER").
        );
    }
}

