package com.example.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.BackEnd.domain.Cliente;
import com.example.BackEnd.domain.Partida;

public interface PartidaRepository extends JpaRepository<Partida, Long>{
	
	@Query(value = "SELECT PA.id, PA.data_fim, PA.data_inicio, PA.hora_fim, PA.hora_inicio, PA.quantidade, PA.etapa_producao_id, PA.maquina_id, PA.status\r\n" + 
			"	FROM partida as PA, etapa_producao as EP, ordem_producao as OP\r\n" + 
			"	WHERE EP.id = PA.etapa_producao_id\r\n" + 
			"	and PA.status LIKE 'pendente'\r\n" + 
			"	and EP.id = :id\r\n" + 
			"	and OP.id =  EP.etapa_producao_id\r\n" + 
			"	ORDER BY OP.prioridade_inicial DESC"  , nativeQuery = true)
	List<Partida> buscarPartidaPorEtapa(@Param("id") Long id);

	List<Partida> findByStatusContaining(String string);

	List<Partida> findByEtapaProducaoIdAndStatus(Long id, String string);

	List<Partida> findAllByEtapaProducaoId(Long id);

    List<Partida> findAllByMaquinaId(Long id);

}
