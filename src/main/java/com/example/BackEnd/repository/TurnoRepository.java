package com.example.BackEnd.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BackEnd.domain.Turno;

//REMOVER IMPORTS NAO UTILIZADOS
public interface TurnoRepository extends JpaRepository<Turno, Long> {

	Turno findTopByOrderByIdDesc();

	Turno findAllById(Long id);
}
