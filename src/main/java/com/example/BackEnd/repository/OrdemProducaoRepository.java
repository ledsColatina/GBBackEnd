package com.example.BackEnd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.BackEnd.domain.OrdemProducao;

public interface OrdemProducaoRepository extends JpaRepository<OrdemProducao, Long>{

	List<OrdemProducao> findByTipoProdutoId(Long id);

	List<OrdemProducao> findByLinhaId(Long id);

	Optional<OrdemProducao> findByReferencia(String ref);
	
	@Query(value = "SELECT OP.id, OP.data_emissao, OP.prioridade_atual, OP.prioridade_inicial, OP.quantidade, OP.referencia, OP.reprocesso, OP.valor_total, OP.cliente_id, OP.linha_id, OP.ordem_producao_id, OP.tipo_produto_id\r\n" + 
			"	FROM ordem_producao as OP, etapa_producao as EP\r\n" + 
			"	WHERE EP.id =:id\r\n" + 
			"	and OP.id = EP.etapa_producao_id;", nativeQuery = true)
	OrdemProducao buscarReferenciOP(@Param("id") Long id);

	List<OrdemProducao> findAllByOrderByPrioridadeAtualDesc();

	@Query(value = "SELECT OP.id, OP.data_emissao, OP.prioridade_atual, OP.prioridade_inicial, OP.quantidade, OP.referencia, OP.reprocesso, OP.valor_total, OP.cliente_id, OP.linha_id, OP.ordem_producao_id, OP.tipo_produto_id FROM ordem_producao OP, etapa_producao ep \n" +
			"WHERE OP.id = ep.etapa_producao_id and ep.id = :id", nativeQuery = true)
	Optional<OrdemProducao> findByEtapaProducaoId(@Param("id") Long id);

	List<OrdemProducao> findByClienteId(Long idCliente);

	List<OrdemProducao> findAllById(Long id);	
	
	@Query(value = "SELECT OP.id, OP.data_emissao, OP.prioridade_atual, OP.prioridade_inicial, OP.quantidade, OP.referencia, OP.reprocesso, OP.valor_total, OP.cliente_id, OP.linha_id, OP.ordem_producao_id, OP.tipo_produto_id\r\n" + 
			"	FROM ordem_producao as OP, cliente as CLI\r\n" + 
			"	where CLI.id = OP.cliente_id\r\n" + 
			"	and CLI.id = :id", nativeQuery = true)
	List<OrdemProducao> buscarOrdemPorCliente(@Param("id") Long id);

	

}
