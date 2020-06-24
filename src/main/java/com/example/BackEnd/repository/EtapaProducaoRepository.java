package com.example.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.BackEnd.domain.EtapaProducao;
import com.example.BackEnd.domain.OrdemProducao;

public interface EtapaProducaoRepository extends JpaRepository<EtapaProducao, Long>{

	
	@Query(value = "SELECT ETA.id, ETA.fim_previsto, ETA.inicio_previsto, ETA.qtd_em_espera, ETA.qtd_em_producao, ETA.qtd_finalizado, ETA.sequencia, ETA.processo_id, ETA.etapa_producao_id\r\n" + 
			"	FROM etapa_producao as ETA, partida as PART\r\n" + 
			"	WHERE PART.id = :id\r\n" + 
			"	and ETA.id = PART.etapa_producao_id ", nativeQuery = true)
	EtapaProducao buscarSequenciaDeEtapa(@Param("id") Long id);
	
	@Query(value = "SELECT EP.id, EP.fim_previsto, EP.inicio_previsto, EP.qtd_em_espera, EP.qtd_em_producao, EP.qtd_finalizado, EP.sequencia, EP.processo_id, EP.etapa_producao_id \r\n" + 
			"	FROM etapa_producao as EP, ordem_producao as OP\r\n" + 
			"	WHERE OP.id = EP.etapa_producao_id\r\n" + 
			"	and OP.id = :id\r\n" + 
			"	and EP.qtd_em_producao <= OP.quantidade\r\n" + 
			"	and EP.qtd_finalizado < OP.quantidade\r\n" + 
			"	ORDER BY EP.sequencia "+
			"LIMIT 1", nativeQuery = true)
	EtapaProducao buscarEtapasDaOPPorSequencia(@Param("id") Long id);
	
	@Query(value = "SELECT EP.id, EP.fim_previsto, EP.inicio_previsto, EP.qtd_em_espera, EP.qtd_em_producao, EP.qtd_finalizado, EP.sequencia, EP.processo_id, EP.etapa_producao_id\r\n" + 
			"	FROM etapa_producao as EP, ordem_producao as OP\r\n" + 
			"	WHERE OP.id = EP.etapa_producao_id\r\n" + 
			"	and OP.id = :id\r\n" + 
			"	and EP.qtd_finalizado < OP.quantidade\r\n" + 
			"	and EP.qtd_em_producao > 0", nativeQuery = true)
	List<EtapaProducao> buscarEtapasdaOPEmProducao(@Param("id") Long id);
	
	

//	
//	@Query(value = "SELECT EP.id, EP.fim_previsto, EP.inicio_previsto, EP.qtd_em_espera, EP.qtd_em_producao, EP.qtd_finalizado, EP.sequencia, EP.processo_id, EP.etapa_producao_id\r\n" + 
//			"	FROM etapa_producao as EP\r\n" + 
//			"	WHERE EP.sequencia = :sequencia\r\n" + 
//			"	and EP.etapa_producao_id = :id", nativeQuery = true)
//	List<EtapaProducao> findByProximasEtapas(int sequencia, Long id);
	
	@Query(value = "SELECT EP.id, EP.fim_previsto, EP.inicio_previsto, EP.qtd_em_espera, EP.qtd_em_producao, EP.qtd_finalizado, EP.sequencia, EP.processo_id, EP.etapa_producao_id\r\n" + 
			"	FROM etapa_producao as EP, ordem_producao as OP\r\n" + 
			"	WHERE OP.id = EP.etapa_producao_id\r\n" + 
			"	and OP.id = :id\r\n" + 
			"	and EP.sequencia = :sequencia\r\n" , nativeQuery = true)
	List<EtapaProducao> buscaPorSequenciaAndEtapaProducaoId(int sequencia, Long id);
	
	@Query(value = "SELECT EP.id, EP.fim_previsto, EP.inicio_previsto, EP.qtd_em_espera, EP.qtd_em_producao, EP.qtd_finalizado, EP.sequencia, EP.processo_id, EP.etapa_producao_id\r\n" + 
			"FROM etapa_producao as EP,ordem_producao as OP\r\n" + 
			"where OP.id = EP.etapa_producao_id\r\n" + 
			"and OP.id = :id\r\n" , nativeQuery = true)
	List<EtapaProducao> findByEtapaProducaoId(@Param("id") Long id);

	
	@Query(value = "SELECT EP.id, EP.fim_previsto, EP.inicio_previsto, EP.qtd_em_espera, EP.qtd_em_producao, EP.qtd_finalizado, EP.sequencia, EP.processo_id, EP.etapa_producao_id\r\n" + 
			"	FROM etapa_producao as EP, ordem_producao as OP\r\n" + 
			"	where EP.etapa_producao_id = OP.id\r\n" + 
			"	and OP.id = :id" , nativeQuery = true)
	List<EtapaProducao> BuscarOrdemPorOP(@Param("id") Long id);

	



	

	

	

}
