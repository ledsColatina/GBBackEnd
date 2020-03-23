package com.example.BackEnd.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BackEnd.domain.Maquina;


public interface MaquinaRepository extends JpaRepository<Maquina, Long> {

	Maquina save(Optional<Maquina> setor);
	Maquina findTopByOrderByIdDesc();
	
}
