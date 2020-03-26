package com.example.BackEnd.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackEnd.domain.Turno;
import com.example.BackEnd.repository.TurnoRepository;

@RestController
@RequestMapping(value = "/turno")
public class TurnoResource {

	@Autowired
	private TurnoRepository turnoRepository;

	//----------------------------------------------------------------------------------------------------------------------
	
	@GetMapping
	public ResponseEntity<List<Turno>> listar() {
		List<Turno> turno = turnoRepository.findAll();
		return !turno.isEmpty() ? ResponseEntity.ok(turno) : ResponseEntity.noContent().build();
	}

	//----------------------------------------------------------------------------------------------------------------------
	
	@GetMapping("{id}")
	public ResponseEntity<?> listarPorTurno(@PathVariable Long id) {
		Turno turno = turnoRepository.findAllById(id);
		return ResponseEntity.ok(turno);
		
	}

	//----------------------------------------------------------------------------------------------------------------------
	@GetMapping("/lastID")
	public ResponseEntity<?> pegarUltimoID() {
		Turno turno = turnoRepository.findTopByOrderByIdDesc();
		if (turno != null)
			return ResponseEntity.ok(turno.getId() + 1);
		else
			return ResponseEntity.ok(1);
	}

	//----------------------------------------------------------------------------------------------------------------------
	
	
		@PostMapping
		public ResponseEntity<Turno> criarTurno(@Valid @RequestBody Turno turno) {
			Turno turnoSalvo = turnoRepository.save(turno);
			return ResponseEntity.status(HttpStatus.OK).body(turnoSalvo);
		}
		
		
		//-----------------------------------------------------------------------------------------------------------------------	
		
			@DeleteMapping("/{id}")
			@ResponseStatus(HttpStatus.NO_CONTENT)
			protected void deleteTurno(@PathVariable Long id) {
				turnoRepository.deleteById(id);
			}
	//----------------------------------------------------------------------------------------------------------------------------   
		    
	@PutMapping("/{id}") 
	public ResponseEntity<Turno> atualizaTurno(@PathVariable("id") Long id,@RequestBody Turno turno,HttpServletResponse responseEntity){
		return turnoRepository.findById(id).map(record -> {
			record.setDescricaoTurno(turno.getDescricaoTurno());
			record.setDiasDaSemana(turno.getDiasDaSemana());
			record.setHoraFim(turno.getHoraFim());
			record.setHoraInicio(turno.getHoraInicio());
			record.setListaMaquina(turno.getListaMaquina());
			Turno updated = turnoRepository.save(record);
			return ResponseEntity.ok().body(updated);
		    	                   	               
		}).orElse(ResponseEntity.notFound().build());
	}   			
					
}
