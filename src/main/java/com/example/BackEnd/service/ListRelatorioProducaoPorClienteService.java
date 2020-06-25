package com.example.BackEnd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BackEnd.domain.Cliente;
import com.example.BackEnd.domain.EtapaProducao;
import com.example.BackEnd.domain.OrdemProducao;
import com.example.BackEnd.dto.FormularioClienteDTO;
import com.example.BackEnd.dto.ListRelatorioProducaoPorClienteDTO;
import com.example.BackEnd.dto.RelatorioCLienteDTO;
import com.example.BackEnd.repository.ClienteRepository;
import com.example.BackEnd.repository.OrdemProducaoRepository;
import com.example.BackEnd.service.mapper.ListRelatorioProducaoPorClienteMapper;
import com.example.BackEnd.service.mapper.RelatorioClienteMapper;
import com.example.BackEnd.service.ListRelatorioProducaoPorClienteService;

@Service
public class ListRelatorioProducaoPorClienteService {
	
	private ListRelatorioProducaoPorClienteMapper relatorioProducaoPorClienteMapper;
	
	private RelatorioClienteMapper relatorioClienteMapper;
	
	@Autowired
	private  ClienteRepository clienteRepository;
	
	@Autowired
	private OrdemProducaoRepository ordemProducaoRepository;
	
	//private GanttService ganttService;
	
	public ListRelatorioProducaoPorClienteService(ClienteRepository clienteRepository) {
		this.relatorioProducaoPorClienteMapper = new ListRelatorioProducaoPorClienteMapper();
		this.clienteRepository = clienteRepository;
		this.relatorioClienteMapper = new RelatorioClienteMapper();
	}
	
	
	

	public List<RelatorioCLienteDTO> buscarRealorioCliente() {
		List<Cliente> list = clienteRepository.findAll();
		List<RelatorioCLienteDTO> listRelatorioClienteDTO;
		List<ListRelatorioProducaoPorClienteDTO> listRelatorioProducaoPorClienteDTO = null;
		List<OrdemProducao> listOPPorCliente = null;
		int total;
		String dataSaida;
		List<EtapaProducao> listEtapas;
		listRelatorioClienteDTO = relatorioClienteMapper.toDto(list);
		
		
		for(int i=0;i<list.size();i++) {
			total=0;
			listOPPorCliente = new ArrayList<>();
			listRelatorioProducaoPorClienteDTO = new ArrayList<>();
			listOPPorCliente = ordemProducaoRepository.buscarOrdemPorCliente(list.get(i).getId());
			//listEtapas = new ArrayList<>();
			for(OrdemProducao op : listOPPorCliente) {
				total = total + op.getQuantidade();	
			}
			
			//for(int j=0;i<listOPPorCliente.size();j++) {
			//	listEtapas = listOPPorCliente.get(j).getListEtapas();
			//	dataSaida = ganttService.buscarFinalEtapa((long) listEtapas.size());
			//	listRelatorioProducaoPorClienteDTO.get(j).setDataSaida(dataSaida);
			//}
			
			
			listRelatorioProducaoPorClienteDTO = relatorioProducaoPorClienteMapper.toDto(listOPPorCliente);
			listRelatorioClienteDTO.get(i).setListRelatorio(listRelatorioProducaoPorClienteDTO);
			listRelatorioClienteDTO.get(i).setTotal(total);
		}
		

		return listRelatorioClienteDTO;
	}

	

	
	
}
