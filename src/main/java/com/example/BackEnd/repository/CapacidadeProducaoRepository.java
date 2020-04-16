package com.example.BackEnd.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.BackEnd.domain.CapacidadeProducao;


@Transactional
public interface CapacidadeProducaoRepository extends JpaRepository<CapacidadeProducao, Long> {

	@Query(value = "SELECT MTP.capacidade_hora, MTP.tipo_produto_id, MTP.maquina_id\r\n" + 
			"	FROM maquina_tipo_produto as MTP, maquina as Ma\r\n" + 
			"	Where Ma.id = MTP.maquina_id\r\n" + 
			"	and Ma.id = :id", nativeQuery = true)
	List<CapacidadeProducao> findAllCapacidadeDeMaquinasPorTipoProduto(@Param("id") Long id);

	List<CapacidadeProducao> findByMaquinaId(Long id);
	
	
	void deleteByTipoProdutoId(Long id);

	

	List<CapacidadeProducao> findByMaquinaIdAndTipoProdutoId(Long id, Long id2);



	@Query(value = "SELECT CAP.dtype, CAP.id, CAP.capacidade_hora, CAP.maquina_id, CAP.tipo_produto_id, CAP.hora_extra_id\r\n" + 
			"	FROM capacidade_producao as CAP\r\n" + 
			"	Where CAP.dtype LIKE 'CapacidadeProducao' \r\n" + 
			"	and CAP.maquina_id = :id", nativeQuery = true)
	List<CapacidadeProducao> findByMaquinaIdAndDtype(Long id);

	

	

}
