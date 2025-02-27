package com.example.jwt_auth.model;
// porque criamos essa classe? o spring exige um objeto que implemente userDatails para que a gente possa autenticar
// e autorizar um usuário

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails { // userDeatails a gente tem objetos especificos para trabalhar com autenticação e autorização
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities; // Lista de permissões ou roles do usuário.

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities; // Define as permissões ou roles do usuário.
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Retorna `true`, indicando que a conta do usuário não está expirada.
    }

    // Indica se a conta do usuário está bloqueada.
    @Override
    public boolean isAccountNonLocked() {
        return true; // Retorna `true`, indicando que a conta não está bloqueada.
    }

    // Indica se as credenciais do usuário (ex.: senha) estão expiradas.
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Retorna `true`, indicando que as credenciais do usuário não estão expiradas.
    }

    // Indica se o usuário está habilitado para acessar o sistema.
    @Override
    public boolean isEnabled() {
        return true; // Retorna `true`, indicando que o usuário está ativo e pode acessar o sistema.
    }



}
