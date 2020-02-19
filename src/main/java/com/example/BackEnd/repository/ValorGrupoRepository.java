package com.example.BackEnd.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.BackEnd.domain.Processos;
import com.example.BackEnd.domain.ValorGrupo;

//IDENTAR CORRETAMENTE
public interface ValorGrupoRepository extends JpaRepository<ValorGrupo, Long> {

	ValorGrupo findTopByOrderByIdDesc();

	List<ValorGrupo> findByProcessoId(Long id);
	

}
