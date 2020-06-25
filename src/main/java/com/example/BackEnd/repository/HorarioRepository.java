package com.example.BackEnd.repository;

import com.example.BackEnd.domain.Horario;
import com.example.BackEnd.domain.PartidaGantt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    List<Horario> findAllByMaquinaIdAndDia(Long id, LocalDate date);

    List<Horario> findAllByOrderByDia();

    List<Horario> findAllByMaquinaIdAndDiaAfterOrderByDiaAscInicioAsc(Long id, LocalDate data);

    List<Horario> findAllByIdNotIn(List<Long> listId);
}
