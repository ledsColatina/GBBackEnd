package com.example.BackEnd.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
}
