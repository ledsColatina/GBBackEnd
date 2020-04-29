package com.example.BackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.BackEnd.domain.EtapaProducao;

public interface EtapaProducaoRepository extends JpaRepository<EtapaProducao, Long>{

	
	@Query(value = "SELECT sequencia\r\n" + 
			"	FROM etapa_producao as ETA, partida as PART\r\n" + 
			"	WHERE PART.id = :id\r\n" + 
			"	and ETA.id = PART.etapa_producao_id ", nativeQuery = true)
	String buscarSequenciaDeEtapa(@Param("id") Long id);

}
