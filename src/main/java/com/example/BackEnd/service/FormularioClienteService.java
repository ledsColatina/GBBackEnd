package com.example.BackEnd.service;

import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BackEnd.domain.Cliente;
import com.example.BackEnd.domain.EtapaProducao;
import com.example.BackEnd.domain.OrdemProducao;
import com.example.BackEnd.dto.FormularioClienteDTO;
import com.example.BackEnd.repository.ClienteRepository;
import com.example.BackEnd.repository.EtapaProducaoRepository;
import com.example.BackEnd.repository.OrdemProducaoRepository;

import com.example.BackEnd.service.mapper.FormularioClienteMapper;

@Service

public class FormularioClienteService {
	@Autowired
	private  ClienteRepository clienteRepository;
	
	@Autowired
	private OrdemProducaoRepository ordemProducaoRepository;
	
	@Autowired
	private EtapaProducaoRepository etapaProducaoRepository;
	
	private FormularioClienteMapper formularioclienteMapper;


	public FormularioClienteService(ClienteRepository clienteRepository) {
		this.formularioclienteMapper = new FormularioClienteMapper();
		this.clienteRepository = clienteRepository;
	}
   
	
	public List<FormularioClienteDTO> buscarFormulariosCliente() {
		List<Cliente> list = clienteRepository.findAll();
		List<FormularioClienteDTO> listClienteDTO;
		List<OrdemProducao> listOP ;
		int quantidadeTotal=0;
		List<EtapaProducao> listEtapas;
		int pecasConcluidas;
		int pecasFaltando;

		listClienteDTO = formularioclienteMapper.toDto(list);
		
		for(FormularioClienteDTO clienteDTO: listClienteDTO) {
			listOP = new ArrayList<>();
			quantidadeTotal =0;
			pecasConcluidas=0;
			pecasFaltando=0;
			listOP = ordemProducaoRepository.findByClienteId(clienteDTO.getIdCliente());
			for(OrdemProducao op: listOP) {
				quantidadeTotal = quantidadeTotal + op.getQuantidade();
				listEtapas = new ArrayList<>();
				
				listEtapas = etapaProducaoRepository.findByEtapaProducaoId(op.getId());
				
				for(EtapaProducao etapa: listEtapas) {
					pecasConcluidas = (int) (pecasConcluidas + etapa.getQtdFinalizado());
					pecasFaltando = (int) (pecasFaltando + etapa.getQtdEmEspera());
				}
				
			}
			
			clienteDTO.setPecasFaltando(pecasFaltando);
			clienteDTO.setPecasConcluidas(pecasConcluidas);
			clienteDTO.setTotalOrdemProducoes(listOP.size());
			clienteDTO.setTotalPecas(quantidadeTotal);
		}
	 return listClienteDTO;
	
	}
	
	
	
	
	
	


}
