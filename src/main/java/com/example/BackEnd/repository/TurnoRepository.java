package com.example.BackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BackEnd.domain.Turno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno, Long> {
	Turno findTopByOrderByIdDesc();

	Turno findAllById(Long id);

	@Query(value = "SELECT t.id, t.descricao_turno, t.dias_da_semana, t.hora_fim, t.hora_inicio FROM maquina_turno mt, turno t WHERE mt.maquina_id = :idMaquina and t.id = mt.turno_id", nativeQuery = true)
	List<Turno> findAllByMaquinaId(@Param("idMaquina") Long idMaquina);

}
