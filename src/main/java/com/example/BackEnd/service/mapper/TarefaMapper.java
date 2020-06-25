package com.example.BackEnd.service.mapper;

import com.example.BackEnd.domain.Horario;
import com.example.BackEnd.domain.Tarefa;
import com.example.BackEnd.dto.HorarioDTO;
import com.example.BackEnd.dto.TarefaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefaMapper extends EntityMapper<TarefaDTO, Tarefa> {

    @Override
    @Mapping(source = "idOP", target = "op.id")
    @Mapping(source = "idEtapa", target = "etapa.id")
    @Mapping(source = "idPartida", target = "partida.id")
    Tarefa toEntity(TarefaDTO dto);

    @Override
    @Mapping(source = "op.id", target = "idOP")
    @Mapping(source = "etapa.id", target = "idEtapa")
    @Mapping(source = "partida.id", target = "idPartida")
    TarefaDTO toDto(Tarefa entity);

    @Override
    List<Tarefa> toEntity(List<TarefaDTO> dtoList);

    @Override
    List<TarefaDTO> toDto(List<Tarefa> entityList);
}
