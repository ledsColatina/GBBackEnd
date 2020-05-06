package com.example.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.BackEnd.domain.Cliente;
import com.example.BackEnd.domain.Partida;

public interface PartidaRepository extends JpaRepository<Partida, Long>{
	
	@Query(value = "SELECT PA.id, PA.data_fim, PA.data_inicio, PA.hora_fim, PA.hora_inicio, PA.quantidade, PA.etapa_producao_id, PA.maquina_id\r\n" + 
			"	FROM partida as PA, etapa_producao as EP\r\n" + 
			"	WHERE EP.id = PA.etapa_producao_id\r\n" + 
			"	and EP.id = :id", nativeQuery = true)
	List<Partida> buscarPartidaPorEtapa(@Param("id") Long id);

}
