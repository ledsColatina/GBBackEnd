package com.example.BackEnd.service;

import java.util.ArrayList;
import java.util.List;

import com.example.BackEnd.repository.CapacidadeProducaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.BackEnd.domain.Maquina;
import com.example.BackEnd.repository.MaquinaRepository;
import com.example.BackEnd.repository.TurnoRepository;

@Service
@RequiredArgsConstructor
public class MaquinaService {
	private final MaquinaRepository maquinaRepository;
	private final TurnoRepository turnoRepository;
	private final PartidaService partidaService;
	private final CapacidadeProducaoRepository capacidadeProducaoRepository;
	
	//----------------------------------------------------------------------------------------------------------------------	

	public Maquina buscarPorId(Long id) throws Exception {
		return maquinaRepository.findById(id).orElseThrow(Exception::new);
	}

	public List<Maquina> buscarOrdenadoId() {
		return maquinaRepository.findAllByOrderById();
	}

	public Long buscarCapacidade(Boolean horaExtra, Long idMaquina, Long idTipoProduto) {
		if(!horaExtra){
			return (long) capacidadeProducaoRepository.findByMaquinaIdAndTipoProdutoId(idMaquina, idTipoProduto, "CapacidadeProducao").getCapacidadeHora();
		}else {
			return (long) capacidadeProducaoRepository.findByMaquinaIdAndTipoProdutoId(idMaquina, idTipoProduto, "CapacidadeProducaoExtra").getCapacidadeHora();
		}
	
	}
	
	public List<Maquina> deleteSetor(@PathVariable Long id) {
		List<Maquina> listaSetor = maquinaRepository.findAll();
		List<Maquina> setorEncontrado = new ArrayList<Maquina>();
		int cont = 0;

		for (int i = 0; i < listaSetor.size(); i++) {
			if (listaSetor.get(i).getId() == id) {
				setorEncontrado.add(listaSetor.get(i));
				maquinaRepository.deleteById(id);
				cont = cont + 1;
			}
		}
		return !setorEncontrado.isEmpty() ? (setorEncontrado) : (setorEncontrado);
	}

	//----------------------------------------------------------------------------------------------------------------------	
}
