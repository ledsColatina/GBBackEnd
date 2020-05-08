package com.example.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.BackEnd.domain.OrdemProducao;

public interface OrdemProducaoRepository extends JpaRepository<OrdemProducao, Long>{

	List<OrdemProducao> findByTipoProdutoId(Long id);

	List<OrdemProducao> findByLinhaId(Long id);
	
	@Query(value = "SELECT OP.id, OP.data_emissao, OP.prioridade_atual, OP.prioridade_inicial, OP.quantidade, OP.referencia, OP.reprocesso, OP.valor_total, OP.cliente_id, OP.linha_id, OP.ordem_producao_id, OP.tipo_produto_id\r\n" + 
			"	FROM ordem_producao as OP, etapa_producao as EP\r\n" + 
			"	WHERE EP.id =:id\r\n" + 
			"	and OP.id = EP.etapa_producao_id;", nativeQuery = true)
	OrdemProducao buscarReferenciOP(@Param("id") Long id);

	//List<OrdemProducao> findAllByOrderByPrioridadeDesc();

	

}
