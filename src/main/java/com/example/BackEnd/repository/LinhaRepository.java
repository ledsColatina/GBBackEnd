package com.example.BackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.BackEnd.domain.Linha;

public interface LinhaRepository extends JpaRepository<Linha, Long>{

	Linha findTopByOrderByIdDesc();
	
}
