package com.example.BackEnd.repository;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackEnd.domain.Setor;
import com.example.BackEnd.domain.Turno;

public interface TurnoRepository extends JpaRepository<Turno, Long> {

	

	Turno findTopByOrderByIdDesc();

	void save( Setor setor);

	

}
