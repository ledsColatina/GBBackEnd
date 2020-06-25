package com.example.BackEnd.repository;

import com.example.BackEnd.domain.Cliente;
import com.example.BackEnd.domain.Tarefa;
import com.example.BackEnd.dto.TarefaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    Optional<Tarefa> findByPartidaId(Long id);

    List<Tarefa> findAllByOrderByPrioridadeDesc();

    List<Tarefa> findAllByEtapaId(Long id);
}
