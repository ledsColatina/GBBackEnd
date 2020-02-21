package com.example.BackEnd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackEnd.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {



	public Optional<Usuario> findByLogin(String login);

}
