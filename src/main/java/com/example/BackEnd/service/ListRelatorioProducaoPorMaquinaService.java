package com.example.BackEnd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BackEnd.domain.EtapaProducao;
import com.example.BackEnd.domain.Maquina;
import com.example.BackEnd.domain.OrdemProducao;
import com.example.BackEnd.domain.Processo;
import com.example.BackEnd.dto.ListRelatorioProducaoPorClienteDTO;
import com.example.BackEnd.dto.ListRelatorioProducaoPorMaquinaDTO;
import com.example.BackEnd.dto.RelatorioMaquinaDTO;
import com.example.BackEnd.repository.EtapaProducaoRepository;
import com.example.BackEnd.repository.MaquinaRepository;
import com.example.BackEnd.repository.OrdemProducaoRepository;
import com.example.BackEnd.repository.ProcessoRepository;
import com.example.BackEnd.service.mapper.ListRelatorioProducaoPorMaquinaMapper;

import com.example.BackEnd.service.mapper.RelatorioMaquinaMapper;

@Service
public class ListRelatorioProducaoPorMaquinaService {
	
	private RelatorioMaquinaMapper relatorioMaquinaMapper;
	
	private ListRelatorioProducaoPorMaquinaMapper listRelatorioProducaoPorMaquinaMapper;
	
	@Autowired
	private MaquinaRepository maquinaRepository;
	
	@Autowired
	private OrdemProducaoRepository ordemProducaoRepository;
	
	@Autowired
	private EtapaProducaoRepository etapaProducaoRepository;
	
	@Autowired
	private ProcessoRepository processoRepository;
	
	public ListRelatorioProducaoPorMaquinaService(MaquinaRepository maquinaRepository) {
		this.listRelatorioProducaoPorMaquinaMapper = new ListRelatorioProducaoPorMaquinaMapper();
		this.maquinaRepository = maquinaRepository;
		this.relatorioMaquinaMapper = new RelatorioMaquinaMapper();
	}

	public List<RelatorioMaquinaDTO> buscarRealorioMaquina() {
		List<Maquina> listMaquinas = maquinaRepository.findAll();
		List<RelatorioMaquinaDTO> listRelatorioMaquinaDTO;
		List<ListRelatorioProducaoPorMaquinaDTO> listRelatorioProducaoPorMaquinaDTO = null;
		List<OrdemProducao> listOP = ordemProducaoRepository.findAll();
		List<EtapaProducao> listEtapas;
		Processo processo;
		List<OrdemProducao> listAux = null;
		int total=0;
		
		listRelatorioMaquinaDTO = relatorioMaquinaMapper.toDto(listMaquinas);
		
		for(int i=0;i<listMaquinas.size();i++) {
			listAux = new ArrayList<>();
			for(OrdemProducao op : listOP) {
				
				listEtapas = new ArrayList<>();
				listEtapas = etapaProducaoRepository.BuscarOrdemPorOP(op.getId());
				for(EtapaProducao etapa: listEtapas) {
					for(Maquina maquina: etapa.getProcesso().getListaMaquina()) {
						if(maquina.getNome() == listMaquinas.get(i).getNome()) {
							
							listAux.add(op);
							total = total + op.getQuantidade();
							
						}
					}
				}
			}
			
			
			
			listRelatorioProducaoPorMaquinaDTO = listRelatorioProducaoPorMaquinaMapper.toDto(listAux);
			listRelatorioMaquinaDTO.get(i).setListRelatorio(listRelatorioProducaoPorMaquinaDTO);
			listRelatorioMaquinaDTO.get(i).setTotal(total);
			total = 0;
		}
		
		return listRelatorioMaquinaDTO;
	}
	
	
}
