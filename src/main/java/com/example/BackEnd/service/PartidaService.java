package com.example.BackEnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.BackEnd.domain.Cliente;
import com.example.BackEnd.domain.EtapaProducao;
import com.example.BackEnd.domain.Maquina;
import com.example.BackEnd.domain.OrdemProducao;
import com.example.BackEnd.domain.Partida;
import com.example.BackEnd.domain.Usuario;
import com.example.BackEnd.dto.PartidaDTO;
import com.example.BackEnd.repository.ClienteRepository;
import com.example.BackEnd.repository.EtapaProducaoRepository;
import com.example.BackEnd.repository.MaquinaRepository;
import com.example.BackEnd.repository.OrdemProducaoRepository;
import com.example.BackEnd.repository.PartidaRepository;
import com.example.BackEnd.repository.UsuarioRepository;
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
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private MaquinaRepository maquinaRepository;
	
	public PartidaService(PartidaRepository partidaRepository) {
		this.partidaListagemMapper = new PartidaListagemMapper();
		this.partidaRepository = partidaRepository;
	}
	
	
	public List<Maquina> getMaquinasDoUsuarioLogado() {
		
		Authentication usuarioLogado = (Authentication) SecurityContextHolder.getContext().getAuthentication();  // tentei dessa forma mas nao deu certo
		if (usuarioLogado != null) {
			usuarioLogado.getName();
         }
		Optional<Usuario> usuario = usuarioRepository.findByLogin(usuarioLogado.getName());
		List<Maquina> listMaquinasDoUsuarioLogado = new ArrayList<Maquina>();
		if(usuario.get().getTipo() == true) {
			listMaquinasDoUsuarioLogado = maquinaRepository.findAll();
		}else {
			for(int i=0;i<usuario.get().getListaMaquina().size();i++) {
				listMaquinasDoUsuarioLogado.add(usuario.get().getListaMaquina().get(i));
			}
		}
		
      return listMaquinasDoUsuarioLogado;
	}
	
	public List<PartidaDTO> buscarPartidasIniciadas() {
		List<Partida> listPartida = new ArrayList<Partida>();
		List<PartidaDTO> lisPartidaDTO;
		
		listPartida = partidaRepository.findByStatusContaining("iniciada");
		lisPartidaDTO =  partidaListagemMapper.toDto(listPartida);
		
		
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
		
		
		return lisPartidaDTO;
	}
	
	private List<Maquina> maquinasComPermissao(EtapaProducao etapaProducao,
			List<Maquina> listMaquinasDoUsuarioLogado) {
		Maquina maquinaLiberada;
		List<Maquina> listMaquinasLiberadas = new ArrayList<Maquina>();
		
		for(Maquina maquinaUsuario: listMaquinasDoUsuarioLogado) {
			maquinaLiberada = new Maquina();
			maquinaLiberada = maquinaRepository.findByIdAndMaquinaId(maquinaUsuario.getId(),etapaProducao.getProcesso().getId());
			if(maquinaLiberada != null) {
				listMaquinasLiberadas.add(maquinaLiberada);
			}
		}
		
		
		return listMaquinasLiberadas;
	}
	
	
	public List<PartidaDTO> consultar()  {
		List<Maquina> listMaquinasDoUsuarioLogado = getMaquinasDoUsuarioLogado();
		List<EtapaProducao> listMesmaSequencia = new ArrayList<>();
		List<OrdemProducao> listOrdemProducao = ordemProducaoRepository.findAllByOrderByPrioridadeAtualDesc();
		EtapaProducao proximaEtapa;
		List<Partida> listPartida = new ArrayList<Partida>();
		List<PartidaDTO> lisPartidaDTO;
		List<Partida> listTotalDePartidas = new ArrayList<Partida>();
		Maquina maquinaLiberada = null;
		
 		for(OrdemProducao ordemProducao : listOrdemProducao) {
 			
 			proximaEtapa = etapaProducaoRepository.buscarEtapasDaOPPorSequencia(ordemProducao.getId());
 			listMesmaSequencia = etapaProducaoRepository.buscaPorSequenciaAndEtapaProducaoId(proximaEtapa.getSequencia(),ordemProducao.getId());

 			//sequenciaAtual = listEtapaProducao.get(0).getSequencia();
 			
 			
 			for(EtapaProducao etapa : listMesmaSequencia) {
 				maquinaLiberada = new Maquina();
 				for(Maquina maquina:listMaquinasDoUsuarioLogado) {
 					maquinaLiberada = maquinaRepository.findByIdAndMaquinaId(maquina.getId(),etapa.getProcesso().getId());
 					
 	 			}
 				if(maquinaLiberada != null) {
 					listPartida = partidaRepository.findByEtapaProducaoIdAndStatus(etapa.getId(),"pendente");
 	 				listTotalDePartidas.addAll(listPartida);
 				}
 				
 			}
		}
 		

 		
 		
		
 		lisPartidaDTO = partidaListagemMapper.toDto(listTotalDePartidas);
		
		
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
			List<Maquina> listMaquinaLiberadas = maquinasComPermissao(etapaProducao,listMaquinasDoUsuarioLogado);
			partDTO.setListMaquinasUsuario(listMaquinaLiberadas);
		}
		
		
		return 	lisPartidaDTO;
	}


	


	

	
}
