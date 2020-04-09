package com.example.BackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.BackEnd.domain.OrdemProducao;

public interface OrdemProducaoRepository extends JpaRepository<OrdemProducao, Long>{

	boolean findByTipoProdutoId(Long id);

	boolean findByLinhaId(Long id);

}
