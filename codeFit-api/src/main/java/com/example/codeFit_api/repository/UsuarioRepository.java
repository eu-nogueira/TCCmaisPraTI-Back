package com.example.codeFit_api.repository;

import com.example.codeFit_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,
        Long> {
    Optional<Usuario> findByUsername(String nomeUsuario);
}
