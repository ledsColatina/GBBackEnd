package com.example.BackEnd.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackEnd.domain.Cliente;
import com.example.BackEnd.domain.HoraExtra;
import com.example.BackEnd.repository.HoraExtraRepository;

@RestController
@RequestMapping(value = "/horaExtra")
public class HoraExtraResource {
	
	@Autowired
    private HoraExtraRepository horaExtraRepository;
	
	@GetMapping
	public ResponseEntity<List<HoraExtra>> listar(){
		List<HoraExtra> horaExtra = horaExtraRepository.findAll();	
		return !horaExtra.isEmpty() ? ResponseEntity.ok(horaExtra) : ResponseEntity.noContent().build();	
	}
	
	
	@GetMapping("/lastID")
	public ResponseEntity<?>  pegarUltimoID(){
		HoraExtra horaExtra = horaExtraRepository.findTopByOrderByIdDesc();
		if (horaExtra != null)
			return ResponseEntity.ok(horaExtra.getId()+1);
		else
			return ResponseEntity.ok(1);
	}
	
	
	@PostMapping()
    public ResponseEntity<HoraExtra> criarHoraExtra(@Valid @RequestBody  HoraExtra horaExtra,HttpServletResponse responseEntity){
		HoraExtra horaExtraSalva = horaExtraRepository.save(horaExtra);
    	return ResponseEntity.status(HttpStatus.OK).body(horaExtraSalva);
    }
	
	
}
