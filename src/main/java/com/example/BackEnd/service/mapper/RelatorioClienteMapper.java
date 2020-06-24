package com.example.BackEnd.service.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;

import com.example.BackEnd.domain.Cliente;
import com.example.BackEnd.dto.ListRelatorioProducaoPorClienteDTO;
import com.example.BackEnd.dto.RelatorioCLienteDTO;

@Mapper(componentModel = "spring")
public class RelatorioClienteMapper  implements EntityMapper<RelatorioCLienteDTO,Cliente>{

	@Override
	public Cliente toEntity(RelatorioCLienteDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelatorioCLienteDTO toDto(Cliente entity) {
		RelatorioCLienteDTO relatorio = new RelatorioCLienteDTO();
		relatorio.setNomeCliente(entity.getNome());
		return relatorio;
	}

	@Override
	public List<Cliente> toEntity(List<RelatorioCLienteDTO> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RelatorioCLienteDTO> toDto(List<Cliente> entityList) {
		List<RelatorioCLienteDTO> listRelatorioDTO = new ArrayList<>();
		for(Cliente cliente : entityList) {
			listRelatorioDTO.add(toDto(cliente));

		}
		return listRelatorioDTO;
	}

}
