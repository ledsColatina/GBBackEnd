package com.example.BackEnd.service.mapper;

import java.util.ArrayList;
import java.util.List;

import com.example.BackEnd.domain.Partida;
import com.example.BackEnd.dto.PartidaDTO;

public class PartidaListagemMapper implements EntityMapper<PartidaDTO,Partida>{

	@Override
	public Partida toEntity(PartidaDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PartidaDTO toDto(Partida entity) {
		PartidaDTO partidaDTO = new PartidaDTO();
		partidaDTO.setIdPartida(entity.getId());
		partidaDTO.setQuantidadePartida(entity.getQuantidade());
		System.out.println("ewgsgsdgrdsgsg");
		partidaDTO.setNomeMaquina(entity.getMaquina().getNome());
		return partidaDTO;
	}

	@Override
	public List<Partida> toEntity(List<PartidaDTO> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PartidaDTO> toDto(List<Partida> entityList) {
		List<PartidaDTO> lisPartidaDTO = new ArrayList<>();
		for(Partida part : entityList) {
			System.out.println(entityList.get(0).getMaquina().getNome());
			lisPartidaDTO.add(toDto(part));
		}
		return lisPartidaDTO;
	}

}
