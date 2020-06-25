package com.example.BackEnd.repository;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.BackEnd.domain.Maquina;
import org.springframework.data.repository.query.Param;


public interface MaquinaRepository extends JpaRepository<Maquina, Long> {

	Maquina save(Optional<Maquina> setor);
	Maquina findTopByOrderByIdDesc();

	List<Maquina> findAllByOrderById();
	
	@Query(value = "SELECT MA.id, MA.max_ocupacao, MA.nome, MA.role, MA.maquina_id\r\n" + 
			"	FROM maquina as MA\r\n" + 
			"	WHERE MA.maquina_id = :processo \r\n" + 
			"	and MA.id = :maquina" , nativeQuery = true)
	Maquina findByIdAndMaquinaId(@Param("maquina") Long maquina,@Param("processo") Long processo);

	
}
