package com.example.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackEnd.domain.HoraExtra;
import com.example.BackEnd.domain.Setor;
import com.example.BackEnd.domain.Turno;

//REMOVER IMPORTS NAO UTILIZADOS
public interface TurnoRepository extends JpaRepository<Turno, Long> {

	Turno findTopByOrderByIdDesc();

	void save(Setor setor);

	

}
