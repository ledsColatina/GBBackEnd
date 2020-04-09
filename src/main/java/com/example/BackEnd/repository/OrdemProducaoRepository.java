package com.example.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.BackEnd.domain.OrdemProducao;

public interface OrdemProducaoRepository extends JpaRepository<OrdemProducao, Long>{

	List<OrdemProducao> findByTipoProdutoId(Long id);

	List<OrdemProducao> findByLinhaId(Long id);

}
