package com.example.BackEnd.service.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.BackEnd.domain.Cliente;
import com.example.BackEnd.domain.OrdemProducao;
import com.example.BackEnd.dto.FormularioClienteDTO;

@Mapper(componentModel = "spring")
public class FormularioClienteMapper implements EntityMapper<FormularioClienteDTO,Cliente>{

	@Override
	public Cliente toEntity(FormularioClienteDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FormularioClienteDTO toDto(Cliente entity) {
		FormularioClienteDTO formularioClienteDTO = new FormularioClienteDTO();
		formularioClienteDTO.setIdCliente(entity.getId());
		formularioClienteDTO.setNomeCliente(entity.getNome());
		return formularioClienteDTO;
	}

	@Override
	public List<Cliente> toEntity(List<FormularioClienteDTO> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FormularioClienteDTO> toDto(List<Cliente> entityList) {
		List<FormularioClienteDTO> listFormularioDTO = new ArrayList<>();
		for(Cliente cliente : entityList) {
			listFormularioDTO.add(toDto(cliente));
		}
		return listFormularioDTO;
	}

/*
	@Override
	public OrdemProducao toEntity(FormularioOrdemProducaoDTO dto) {
		return null;
	}

	@Override
	public FormularioOrdemProducaoDTO toDto(OrdemProducao entity) {
		FormularioOrdemProducaoDTO formularioClienteDTO = new FormularioOrdemProducaoDTO();
		FormularioOrdemProducaoDTO..setIdCliente(entity.getId());
		FormularioOrdemProducaoDTO.setNomeCliente(entity.getNome());
		return formularioClienteDTO;
	}

	@Override
	public List<Cliente> toEntity(List<FormularioOrdemProducaoDTO> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FormularioOrdemProducaoDTO> toDto(List<Cliente> entityList) {
		List<FormularioOrdemProducaoDTO> listFormularioDTO = new ArrayList();
		for(Cliente cliente : entityList) {
			listFormularioDTO.add(toDto(cliente));
		}
		return listFormularioDTO;
	}
*/
}
