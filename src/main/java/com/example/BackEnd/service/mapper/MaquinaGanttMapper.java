package com.example.BackEnd.service.mapper;

import com.example.BackEnd.domain.Maquina;
import com.example.BackEnd.domain.Partida;
import com.example.BackEnd.dto.MaquinaGanttDTO;
import com.example.BackEnd.dto.PartidaGanttDTO;

import java.util.ArrayList;
import java.util.List;

public class MaquinaGanttMapper implements EntityMapper<MaquinaGanttDTO, Maquina>{


	@Override
	public Maquina toEntity(MaquinaGanttDTO dto) {
		return null;
	}

	@Override
	public MaquinaGanttDTO toDto(Maquina entity) {
		MaquinaGanttDTO maquinaGanttDTO = new MaquinaGanttDTO();
		maquinaGanttDTO.setIdMaquina(entity.getId());
		maquinaGanttDTO.setNomeMaquina(entity.getNome());
		return maquinaGanttDTO;
	}

	@Override
	public List<Maquina> toEntity(List<MaquinaGanttDTO> dtoList) {
		return null;
	}

	@Override
	public List<MaquinaGanttDTO> toDto(List<Maquina> entityList) {
		return null;
	}
}
