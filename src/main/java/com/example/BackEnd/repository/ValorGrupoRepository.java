package com.example.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BackEnd.domain.ValorGrupo;

public interface ValorGrupoRepository extends JpaRepository<ValorGrupo, Long> {

	ValorGrupo findTopByOrderByIdDesc();

	List<ValorGrupo> findBySubProcessoId(Long id);

	//List<ValorGrupo> findByProcessoId(Long id);
}
