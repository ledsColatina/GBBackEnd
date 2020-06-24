package com.example.BackEnd.service.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;

import com.example.BackEnd.domain.OrdemProducao;
import com.example.BackEnd.dto.ListRelatorioProducaoPorClienteDTO;
import com.example.BackEnd.dto.ListRelatorioProducaoPorMaquinaDTO;

@Mapper(componentModel = "spring")
public class ListRelatorioProducaoPorMaquinaMapper implements EntityMapper<ListRelatorioProducaoPorMaquinaDTO,OrdemProducao>{

	@Override
	public OrdemProducao toEntity(ListRelatorioProducaoPorMaquinaDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListRelatorioProducaoPorMaquinaDTO toDto(OrdemProducao entity) {
		ListRelatorioProducaoPorMaquinaDTO list = new ListRelatorioProducaoPorMaquinaDTO();
		list.setIdOrdem(entity.getId());
		list.setPrioridade(entity.getPrioridadeAtual());
		list.setQuantidade(entity.getQuantidade());
		list.setRefCliente(entity.getReferencia());
		list.setNomeCliente(entity.getCliente().getNome());
		return list;
	}

	@Override
	public List<OrdemProducao> toEntity(List<ListRelatorioProducaoPorMaquinaDTO> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ListRelatorioProducaoPorMaquinaDTO> toDto(List<OrdemProducao> entityList) {
		List<ListRelatorioProducaoPorMaquinaDTO> listRelatorioDTO = new ArrayList<>();
		for(OrdemProducao op : entityList) {
			listRelatorioDTO.add(toDto(op));
		}

		return listRelatorioDTO;
	}

}
