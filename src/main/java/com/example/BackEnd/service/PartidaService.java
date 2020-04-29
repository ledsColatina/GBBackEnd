package com.example.BackEnd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BackEnd.dto.PartidaDTO;
import com.example.BackEnd.repository.EtapaProducaoRepository;
import com.example.BackEnd.repository.PartidaRepository;
import com.example.BackEnd.service.mapper.PartidaListagemMapper;

@Service
public class PartidaService {
	
	
	private PartidaListagemMapper partidaListagemMapper;
	
	@Autowired
	private PartidaRepository partidaRepository;
	
	@Autowired
	private EtapaProducaoRepository etapaProducaoRepository;
	
	public PartidaService(PartidaRepository partidaRepository) {
		this.partidaListagemMapper = new PartidaListagemMapper();
		this.partidaRepository = partidaRepository;
	}
	
	public List<PartidaDTO> consultar() {
		List<PartidaDTO> lisPartidaDTO = partidaListagemMapper.toDto(partidaRepository.findAll());
		
		for(PartidaDTO partDTO : lisPartidaDTO) {
			partDTO.setSequenciaEtapa(etapaProducaoRepository.buscarSequenciaDeEtapa(partDTO.getIdPartida()));
		}
		return 	lisPartidaDTO;
	}
}
