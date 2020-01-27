package com.example.BackEnd.web;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.BackEnd.domain.Turno;
import com.example.BackEnd.repository.TurnoRepository;

@RestController
@RequestMapping(value = "/turno")
public class TurnoResource {
	
	@Autowired
    private TurnoRepository turnoRepository;
	
	@GetMapping
	public ResponseEntity<List<Turno>> listar(){
		List<Turno> turno = turnoRepository.findAll();	
		return !turno.isEmpty() ? ResponseEntity.ok(turno) : ResponseEntity.noContent().build();	
	}
	
	
	@GetMapping("/lastID")
	public ResponseEntity<?>  pegarUltimoID(){
		Turno turno = turnoRepository.findTopByOrderByIdDesc();
		if (turno != null)
			return ResponseEntity.ok(turno.getId()+1);
		else
			return ResponseEntity.ok(1);
	}
	
	 
}
