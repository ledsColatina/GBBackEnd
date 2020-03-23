package com.example.BackEnd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.BackEnd.domain.Maquina;
import com.example.BackEnd.repository.MaquinaRepository;
import com.example.BackEnd.repository.TurnoRepository;

@Service
public class MaquinaService {
	@Autowired
	private MaquinaRepository setorRepository;

	@Autowired
	private TurnoRepository turnoRepository;
	
	//----------------------------------------------------------------------------------------------------------------------	
	
	
	public List<Maquina> deleteSetor(@PathVariable Long id) {
		List<Maquina> listaSetor = setorRepository.findAll();
		List<Maquina> setorEncontrado = new ArrayList<Maquina>();
		int cont = 0;

		for (int i = 0; i < listaSetor.size(); i++) {
			if (listaSetor.get(i).getId() == id) {
				setorEncontrado.add(listaSetor.get(i));
				setorRepository.deleteById(id);
				cont = cont + 1;
			}
		}
		return !setorEncontrado.isEmpty() ? (setorEncontrado) : (setorEncontrado);
	}
	
	//----------------------------------------------------------------------------------------------------------------------	
}
