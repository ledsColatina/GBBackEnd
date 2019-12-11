package com.example.BackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.BackEnd.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
