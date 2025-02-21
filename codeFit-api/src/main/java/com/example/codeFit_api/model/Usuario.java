package com.example.codeFit_api.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_usuario;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String senha;
}
