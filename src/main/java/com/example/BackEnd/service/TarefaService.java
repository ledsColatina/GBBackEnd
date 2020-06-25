package com.example.BackEnd.service;

import com.example.BackEnd.domain.Tarefa;
import com.example.BackEnd.dto.TarefaDTO;
import com.example.BackEnd.repository.TarefaRepository;
import com.example.BackEnd.service.mapper.TarefaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final TarefaMapper tarefaMapper;

    public void guardarTarefa(TarefaDTO tarefa) {
        Tarefa tarefa1 = tarefaMapper.toEntity(tarefa);
        tarefaRepository.save(tarefa1);
    }

    public Tarefa buscarPorIdPartida(Long id) throws Exception {
        return tarefaRepository.findByPartidaId(id).orElseThrow(Exception::new);
    }

    public List<Tarefa> buscarPorEtapa(Long id){
        return tarefaRepository.findAllByEtapaId(id);
    }

    public void excluirTarefa(Long id) throws Exception {
        Tarefa tarefa = buscarPorIdPartida(id);
        tarefaRepository.delete(tarefa);
    }

    public List<TarefaDTO> listarTarefas() {
        return tarefaMapper.toDto(tarefaRepository.findAllByOrderByPrioridadeDesc());
    }

}
