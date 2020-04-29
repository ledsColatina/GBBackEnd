package com.example.BackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackEnd.domain.Cliente;
import com.example.BackEnd.domain.Partida;

public interface PartidaRepository extends JpaRepository<Partida, Long>{

}
