package com.example.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.BackEnd.domain.CapacidadeProducaoExtra;

public interface CapacidadeProducaoExtraRepository extends JpaRepository<CapacidadeProducaoExtra, Long>{

	List<CapacidadeProducaoExtra> findByHoraExtraId(Long id);

}
