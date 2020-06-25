package com.example.BackEnd.service.mapper;

import com.example.BackEnd.domain.Partida;
import com.example.BackEnd.domain.PartidaGantt;
import com.example.BackEnd.dto.PartidaDTO;
import com.example.BackEnd.dto.PartidaGanttDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PartidaGanttMapper extends EntityMapper<PartidaGanttDTO, PartidaGantt>{

	@Override
	@Mapping(source = "idPartida", target = "partida.id")
	@Mapping(source = "idHorario", target = "horario.id")
	@Mapping(source = "idMaquina", target = "maquina.id")
	PartidaGantt toEntity(PartidaGanttDTO dto);

	@Override
	@Mapping(source = "partida.id", target = "idPartida")
	@Mapping(source = "horario.id", target = "idHorario")
	@Mapping(source = "maquina.id", target = "idMaquina")
	PartidaGanttDTO toDto(PartidaGantt entity);
}
