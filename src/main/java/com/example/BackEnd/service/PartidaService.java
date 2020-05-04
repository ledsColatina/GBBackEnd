package com.example.BackEnd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BackEnd.domain.Cliente;
import com.example.BackEnd.domain.EtapaProducao;
import com.example.BackEnd.domain.OrdemProducao;
import com.example.BackEnd.dto.PartidaDTO;
import com.example.BackEnd.repository.ClienteRepository;
import com.example.BackEnd.repository.EtapaProducaoRepository;
import com.example.BackEnd.repository.OrdemProducaoRepository;
import com.example.BackEnd.repository.PartidaRepository;
import com.example.BackEnd.service.mapper.PartidaListagemMapper;

@Service
public class PartidaService {
	
	
	private PartidaListagemMapper partidaListagemMapper;
	
	@Autowired
	private PartidaRepository partidaRepository;
	
	@Autowired
	private EtapaProducaoRepository etapaProducaoRepository;
	
	@Autowired
	private OrdemProducaoRepository ordemProducaoRepository;
	
	private ClienteRepository clienteRepository;
	
	public PartidaService(PartidaRepository partidaRepository) {
		this.partidaListagemMapper = new PartidaListagemMapper();
		this.partidaRepository = partidaRepository;
	}
	
	public List<PartidaDTO> consultar() {
		System.out.println("ewgsgsdgrdsgsg");
		List<PartidaDTO> lisPartidaDTO = partidaListagemMapper.toDto(partidaRepository.findAll());
		
		EtapaProducao etapaProducao;
		OrdemProducao ordeProducao;
		
		for(PartidaDTO partDTO : lisPartidaDTO) {
			etapaProducao = new EtapaProducao();
			ordeProducao = new OrdemProducao();
			etapaProducao = etapaProducaoRepository.buscarSequenciaDeEtapa(partDTO.getIdPartida());
			partDTO.setSequenciaEtapa(etapaProducao.getSequencia());
			ordeProducao  = ordemProducaoRepository.buscarReferenciOP(etapaProducao.getId());
			partDTO.setReferenciaOP(ordeProducao.getReferencia());
			partDTO.setNomeCliente(ordeProducao.getCliente().getNome());
		}
		
		return 	lisPartidaDTO;
	}
}
