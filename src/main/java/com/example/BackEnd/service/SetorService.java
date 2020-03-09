package com.example.BackEnd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.BackEnd.domain.Setor;
import com.example.BackEnd.repository.SetorRepository;
import com.example.BackEnd.repository.TurnoRepository;

@Service
public class SetorService {
	@Autowired
	private SetorRepository setorRepository;

	@Autowired
	private TurnoRepository turnoRepository;
	
	//----------------------------------------------------------------------------------------------------------------------	
	
	
	public List<Setor> deleteSetor(@PathVariable Long id) {
		List<Setor> listaSetor = setorRepository.findAll();
		List<Setor> setorEncontrado = new ArrayList<Setor>();
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
