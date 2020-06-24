package com.example.BackEnd.service.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;

import com.example.BackEnd.domain.Cliente;
import com.example.BackEnd.domain.Maquina;
import com.example.BackEnd.dto.RelatorioCLienteDTO;
import com.example.BackEnd.dto.RelatorioMaquinaDTO;

@Mapper(componentModel = "spring")
public class RelatorioMaquinaMapper implements EntityMapper<RelatorioMaquinaDTO,Maquina>{

	@Override
	public Maquina toEntity(RelatorioMaquinaDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelatorioMaquinaDTO toDto(Maquina entity) {
		RelatorioMaquinaDTO relatorio = new RelatorioMaquinaDTO();
		relatorio.setNomeMaquina(entity.getNome());
		return relatorio;
	}

	@Override
	public List<Maquina> toEntity(List<RelatorioMaquinaDTO> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RelatorioMaquinaDTO> toDto(List<Maquina> entityList) {
		List<RelatorioMaquinaDTO> listRelatorioDTO = new ArrayList<>();
		for(Maquina maquina : entityList) {
			listRelatorioDTO.add(toDto(maquina));

		}
		return listRelatorioDTO;
	}

}
