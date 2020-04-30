package com.example.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.BackEnd.domain.OrdemProducao;

public interface OrdemProducaoRepository extends JpaRepository<OrdemProducao, Long>{

	List<OrdemProducao> findByTipoProdutoId(Long id);

	List<OrdemProducao> findByLinhaId(Long id);
	
	@Query(value = "SELECT id, data_emissao, prioridade_atual, prioridade_inicial, quantidade, referencia, reprocesso, valor_total, cliente_id, linha_id, ordem_producao_id, tipo_produto_id\r\n" + 
			"	FROM ordem_producao as OP, etapa_producao as EP\r\n" + 
			"	WHERE EP.id = :id\r\n" + 
			"	and OP.ordem_producao_id = EP.id;", nativeQuery = true)
	OrdemProducao buscarReferenciOP(@Param("id") Long id);

}
