package com.example.BackEnd.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.BackEnd.domain.CapacidadeProducaoExtra;

@Transactional
public interface CapacidadeProducaoExtraRepository extends JpaRepository<CapacidadeProducaoExtra, Long>{

	List<CapacidadeProducaoExtra> findByHoraExtraId(Long id);

	void deleteByTipoProdutoId(Long id);

	void deleteByHoraExtraId(Long id);
}
