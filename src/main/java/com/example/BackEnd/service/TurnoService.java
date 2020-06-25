package com.example.BackEnd.service;

import com.example.BackEnd.domain.Tarefa;
import com.example.BackEnd.domain.Turno;
import com.example.BackEnd.dto.TarefaDTO;
import com.example.BackEnd.repository.TarefaRepository;
import com.example.BackEnd.repository.TurnoRepository;
import com.example.BackEnd.service.mapper.TarefaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TurnoService {

    private final TurnoRepository turnoRepository;

    public List<Turno> listarTodos() {
        return turnoRepository.findAll();
    }

    public Turno buscarPorId(Long id) throws Exception {
        return turnoRepository.findById(id).orElseThrow(Exception::new);
    }

    public List<Turno> buscarPorMaquina(Long id) {
        return turnoRepository.findAllByMaquinaId(id);
    }

    public Long duracaoTurno(Turno turnoAux) throws Exception {
        Turno turno = buscarPorId(turnoAux.getId());
        Long horaInicio = Long.parseLong(turno.getHoraInicio().substring(0,2));
        Long horaFim = Long.parseLong(turno.getHoraFim().substring(0, 2));
        Long minutoInicio = Long.parseLong(turno.getHoraInicio().substring(3,5));
        Long minutoFim = Long.parseLong(turno.getHoraFim().substring(3,5));
        Long totalHora = horaFim - horaInicio;
        Long totalMinuto = (minutoFim-minutoInicio)/60;

        return totalHora+totalMinuto;
    }
}
