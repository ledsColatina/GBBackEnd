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

import com.example.BackEnd.domain.Linha;
import com.example.BackEnd.repository.LinhaRepository;

@RestController
@RequestMapping(value = "/linha")
public class LinhaResource {

	@Autowired
	private LinhaRepository linhaRepository;

	//----------------------------------------------------------------------------------------------------------------------------
	
	@GetMapping
	protected ResponseEntity<List<Linha>> listar() {
		List<Linha> linha = linhaRepository.findAll();
		return !linha.isEmpty() ? ResponseEntity.ok(linha) : ResponseEntity.noContent().build();
	}
	
	//----------------------------------------------------------------------------------------------------------------------------

	@GetMapping("/lastID")
	protected ResponseEntity<?> pegarUltimoIDLinha() {
		Linha linha = linhaRepository.findTopByOrderByIdDesc();
		if (linha != null)
			return ResponseEntity.ok(linha.getId() + 1);
		else
			return ResponseEntity.ok(1); 

	}

	//----------------------------------------------------------------------------------------------------------------------------
	
	@PostMapping
	protected ResponseEntity<Linha> criarLinha(@Valid @RequestBody Linha linha, HttpServletResponse responseEntity) {
		Linha linhaSalvo = linhaRepository.save(linha);
		return ResponseEntity.status(HttpStatus.OK).body(linhaSalvo);
	}

	//----------------------------------------------------------------------------------------------------------------------------
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	protected void deleteLinha(@PathVariable Long id) {
		linhaRepository.deleteById(id);
	}

	//----------------------------------------------------------------------------------------------------------------------------
	
	@PutMapping("/{id}")
	protected ResponseEntity<Linha> atualizaLinha(@PathVariable("id") Long id, @RequestBody Linha linha,
			HttpServletResponse responseEntity) {
		return linhaRepository.findById(id).map(record -> {
			record.setDescricao(linha.getDescricao());
			Linha updated = linhaRepository.save(record);
			return ResponseEntity.ok().body(updated);

		}).orElse(ResponseEntity.notFound().build());
	}
	
	//----------------------------------------------------------------------------------------------------------------------------
}
