package com.example.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.example.BackEnd.domain.Processos;


public interface ProcessosRepository extends JpaRepository<Processos, Long> {
	@Query(value = "SELECT P.id, P.descricao, P.setor_id\n" + 
			"  FROM processos as P, setor as S\n" + 
			"  WHERE P.setor_id = S.id AND\n" + 
			"	S.id = :id", nativeQuery = true)
	List<Processos> findAllProcessosDoSetor(@Param("id") Long id);
	
	


	
	

}
