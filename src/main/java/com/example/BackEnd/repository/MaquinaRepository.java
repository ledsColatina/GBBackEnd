package com.example.BackEnd.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.BackEnd.domain.Maquina;


public interface MaquinaRepository extends JpaRepository<Maquina, Long> {

	Maquina save(Optional<Maquina> setor);
	Maquina findTopByOrderByIdDesc();
	
	@Query(value = "SELECT MA.id, MA.max_ocupacao, MA.nome, MA.role, MA.maquina_id\r\n" + 
			"	FROM maquina as MA\r\n" + 
			"	WHERE MA.maquina_id = :processo \r\n" + 
			"	and MA.id = :maquina" , nativeQuery = true)
	Maquina findByIdAndMaquinaId(Long maquina, Long processo);
	
	
}
