package com.example.BackEnd.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackEnd.domain.CapacidadeProducao;
import com.example.BackEnd.domain.CapacidadeProducaoExtra;


@RestController
@RequestMapping(value = "/capacidadeproducaoextra")
public class CapacidadeProducaoExtraResource {
	@Autowired
    private com.example.BackEnd.repository.CapacidadeProducaoExtraRepository CapacidadeProducaoExtraRepository;
	
	
	@GetMapping
	public ResponseEntity<List<CapacidadeProducaoExtra>> listar(){
		List<CapacidadeProducaoExtra> capacidade = CapacidadeProducaoExtraRepository.findAll();	
		return !capacidade.isEmpty() ? ResponseEntity.ok(capacidade) : ResponseEntity.noContent().build();	
	}
	
	//-----------------------------------------------------------------------------------------------------------------------	
	
	@PutMapping("/{id}")
	protected ResponseEntity<CapacidadeProducaoExtra> atualizaCapacidade(@PathVariable("id") Long id,  @RequestBody CapacidadeProducaoExtra capacidadeProducao,HttpServletResponse responseEntity) {
		return CapacidadeProducaoExtraRepository.findById(id).map(record -> {
			record.setCapacidadeHora(capacidadeProducao.getCapacidadeHora());
			CapacidadeProducaoExtra updated = CapacidadeProducaoExtraRepository.save(record);
			return ResponseEntity.ok().body(updated);

		}).orElse(ResponseEntity.notFound().build());
	}
	
	
}
