package com.example.BackEnd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BackEnd.domain.Cliente;
import com.example.BackEnd.domain.EtapaProducao;
import com.example.BackEnd.domain.OrdemProducao;
import com.example.BackEnd.domain.Partida;
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
	
	
	
	public PartidaService(PartidaRepository partidaRepository) {
		this.partidaListagemMapper = new PartidaListagemMapper();
		this.partidaRepository = partidaRepository;
	}
	
	public List<PartidaDTO> consultar(int op) {
		int sequenciaAtual;
		List<EtapaProducao> listMesmaSequencia = new ArrayList<EtapaProducao>();
		List<OrdemProducao> listOrdemProducao = ordemProducaoRepository.findAll();
		List<EtapaProducao> listEtapaProducao;
		
 		for(OrdemProducao ordemProducao : listOrdemProducao) {
 			if(op == 0) {
 				listEtapaProducao = etapaProducaoRepository.buscarEtapasDaOPPorSequencia(ordemProducao.getId());
 			}else {
 				listEtapaProducao = etapaProducaoRepository.buscarEtapasdaOPEmProducao(ordemProducao.getId());
 			}
 			
 			sequenciaAtual = listEtapaProducao.get(0).getSequencia();
 			for(EtapaProducao etapa : listEtapaProducao) {
 				if(etapa.getSequencia() == sequenciaAtual){
 					System.out.println(sequenciaAtual);
 					listMesmaSequencia.add(etapa);
 				}
 			}
		}
 		
 		List<Partida> listPartida = new ArrayList<Partida>();
 		List<Partida> listPartidaPesquisada = new ArrayList<Partida>();;
 		for(EtapaProducao etapaProd: listMesmaSequencia) {
 			listPartidaPesquisada = partidaRepository.buscarPartidaPorEtapa(etapaProd.getId());
 			for(Partida partida : listPartidaPesquisada) {
 				listPartida.add(partida);
 			}
 		}
 		
		List<PartidaDTO> lisPartidaDTO = partidaListagemMapper.toDto(listPartida);
		
		
		
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
