package com.example.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



import com.example.BackEnd.domain.SubProcesso;

public interface SubProcessosRepository extends JpaRepository<SubProcesso, Long> {
	
	List<SubProcesso> findByProcessoId(Long id);
}
