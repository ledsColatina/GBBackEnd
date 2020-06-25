package com.example.BackEnd.service.mapper;

import com.example.BackEnd.domain.Horario;
import com.example.BackEnd.dto.HorarioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring")
public interface HorarioMapper extends EntityMapper<HorarioDTO, Horario> {

    @Override
    @Mapping(source = "idTurno", target = "turno.id")
    @Mapping(source = "idMaquina", target = "maquina.id")
    Horario toEntity(HorarioDTO dto);

    @Override
    @Mapping(source = "turno.id", target = "idTurno")
    @Mapping(source = "maquina.id", target = "idMaquina")
    HorarioDTO toDto(Horario entity);

    @Override
    List<Horario> toEntity(List<HorarioDTO> dtoList);

    @Override
    List<HorarioDTO> toDto(List<Horario> entityList);
}
