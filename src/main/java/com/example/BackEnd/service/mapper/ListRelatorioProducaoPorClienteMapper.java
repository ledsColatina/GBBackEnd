package com.example.BackEnd.service.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;

import com.example.BackEnd.domain.Cliente;
import com.example.BackEnd.domain.OrdemProducao;
import com.example.BackEnd.dto.FormularioClienteDTO;
import com.example.BackEnd.dto.ListRelatorioProducaoPorClienteDTO;

@Mapper(componentModel = "spring")
public class ListRelatorioProducaoPorClienteMapper implements EntityMapper<ListRelatorioProducaoPorClienteDTO,OrdemProducao>{

	@Override
	public OrdemProducao toEntity(ListRelatorioProducaoPorClienteDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListRelatorioProducaoPorClienteDTO toDto(OrdemProducao entity) {
		ListRelatorioProducaoPorClienteDTO list = new ListRelatorioProducaoPorClienteDTO();
		list.setIdOrdem(entity.getId());
		list.setPrioridade(entity.getPrioridadeAtual());
		list.setRefCliente(entity.getReferencia());
		list.setQuantidade(entity.getQuantidade());
		return list;
	}

	@Override
	public List<OrdemProducao> toEntity(List<ListRelatorioProducaoPorClienteDTO> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ListRelatorioProducaoPorClienteDTO> toDto(List<OrdemProducao> entityList) {
		List<ListRelatorioProducaoPorClienteDTO> listRelatorioDTO = new ArrayList<>();
		for(OrdemProducao op : entityList) {
			listRelatorioDTO.add(toDto(op));
		}

		return listRelatorioDTO;
	}

	

}
