package com.example.BackEnd.repository;

import com.example.BackEnd.domain.PartidaGantt;
import com.example.BackEnd.domain.Tarefa;
import com.example.BackEnd.dto.PartidaGanttDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartidaGanttRepository extends JpaRepository<PartidaGantt, Long> {

    List<PartidaGantt> findAllByHorarioId(Long id);

    List<PartidaGantt> findByPartidaId(Long id);

    List<PartidaGantt> findAllByOrderByMaquinaId();

    List<PartidaGantt> findAllByReferenciaContainingIgnoreCase(String ref);

    List<PartidaGantt> findAllByReferenciaNotIn(List<String> referencias);

}
