package com.example.BackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.BackEnd.domain.EtapaProducao;

public interface EtapaProducaoRepository extends JpaRepository<EtapaProducao, Long>{

	
	@Query(value = "id, fim_previsto, inicio_previsto, qtd_em_espera, qtd_em_producao, qtd_finalizado, sequencia, processo_id, etapa_producao_id\r\n" + 
			"	FROM etapa_producao as ETA, partida as PART\r\n" + 
			"	WHERE PART.id = :id\r\n" + 
			"	and ETA.id = PART.etapa_producao_id ", nativeQuery = true)
	EtapaProducao buscarSequenciaDeEtapa(@Param("id") Long id);

}
