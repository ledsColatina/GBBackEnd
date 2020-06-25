package com.example.BackEnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.BackEnd.service.mapper.PartidaGanttMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;


import com.example.BackEnd.domain.EtapaProducao;
import com.example.BackEnd.domain.Maquina;
import com.example.BackEnd.domain.OrdemProducao;
import com.example.BackEnd.domain.Partida;
import com.example.BackEnd.domain.Usuario;
import com.example.BackEnd.dto.PartidaDTO;
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
	private OrdemProducaoService ordemProducaoService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private MaquinaRepository maquinaRepository;
	
	public PartidaService(PartidaRepository partidaRepository) {
		this.partidaListagemMapper = new PartidaListagemMapper();
		this.partidaRepository = partidaRepository;
	}

	public List<Partida> buscarPorEtapa(Long id) {
		return partidaRepository.findAllByEtapaProducaoId(id);
	}

	public Partida buscarPorId(Long id) throws Exception {
		return partidaRepository.findById(id).orElseThrow(Exception::new);
	}

	public List<Partida> buscarPorMaquina(Long id) {
		return partidaRepository.findAllByMaquinaId(id);
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
		Maquina maquinaLiberada ;
		boolean temPermissao = false;
		boolean etapaIniciada = false;
		List<Partida> partidasEtapa = new ArrayList<>();
		List<Partida> listAuxiliarDePartidas ;
		List<Partida> partidaIniciada= new ArrayList<>();
		List<Partida> listPartidasDisponivel ;
		boolean disponivel=false;

 		for(OrdemProducao ordemProducao : listOrdemProducao) {
 			proximaEtapa = etapaProducaoRepository.buscarEtapasDaOPPorSequencia(ordemProducao.getId());
 			if(proximaEtapa != null){
				listMesmaSequencia = etapaProducaoRepository.buscaPorSequenciaAndEtapaProducaoId(proximaEtapa.getSequencia(),ordemProducao.getId());
			}
 			else {
 				listMesmaSequencia = new ArrayList<>();
			}

 			listAuxiliarDePartidas = new ArrayList<>();
 			listPartidasDisponivel = new ArrayList<>();
 			for(EtapaProducao etapa : listMesmaSequencia) {
 				maquinaLiberada = new Maquina();
				partidasEtapa = new ArrayList<>();
 				for(Maquina maquina:listMaquinasDoUsuarioLogado) {
 					if(maquinaRepository.findByIdAndMaquinaId(maquina.getId(),etapa.getProcesso().getId()) != null) {
 						temPermissao = true;
 					}
 	 			}
 				if(temPermissao) { 

					if(partidaRepository.findByEtapaProducaoIdAndStatus(etapa.getId(),"pendente").isEmpty() && !partidaRepository.findByEtapaProducaoIdAndStatus(etapa.getId(),"iniciada").isEmpty()) {
						etapaIniciada = true;
						disponivel = true;
						listPartidasDisponivel.addAll(partidaRepository.findByEtapaProducaoIdAndStatus(etapa.getId(),"disponivel"));
						for(Partida partida: listPartidasDisponivel) {
							listTotalDePartidas.add(partida);
						}
					} 
					else {						
						
						partidasEtapa.addAll(partidaRepository.findByEtapaProducaoIdAndStatus(etapa.getId(),"pendente"));
						for(Partida partida: partidasEtapa) {
							listAuxiliarDePartidas.add(partida);
			 			}
					}
 				}
 			}
 			if(!etapaIniciada ) {
				listTotalDePartidas.addAll((listAuxiliarDePartidas));
			}
 			etapaIniciada = false;
		}
 		
 	//-----------------------------------------------------------------------------------------------------------
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
	
	private void mudarStatusPartidaEmDisponivel(Long id){
		List<Partida> listaPartidas = new ArrayList<>();
		listaPartidas = partidaRepository.findAllByEtapaProducaoId(id);
		for(Partida partida:listaPartidas) {
			partida.setStatus("disponivel");
		}
	}

}
