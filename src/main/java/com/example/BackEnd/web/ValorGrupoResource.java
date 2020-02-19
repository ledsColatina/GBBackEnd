package com.example.BackEnd.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import com.example.BackEnd.domain.LogValor;
import com.example.BackEnd.domain.Processos;
import com.example.BackEnd.domain.ValorGrupo;
import com.example.BackEnd.repository.LogValorRepository;
import com.example.BackEnd.repository.ValorGrupoRepository;

@RestController
@RequestMapping(value = "/valorGrupo")
public class ValorGrupoResource {

	@Autowired
	private ValorGrupoRepository valorGrupoRepository;

	@Autowired
	private LogValorRepository logValorRepository;

	@PostMapping()
	protected ResponseEntity<ValorGrupo> criarValorGrupo(@Valid @RequestBody ValorGrupo valorGrupo,HttpServletResponse responseEntity) {
		//Salva o Objeto de valorGrupo
		ValorGrupo valorGrupoSalvo = valorGrupoRepository.save(valorGrupo);
		
		SimpleDateFormat formatadorDataDeHoje = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String dataDeHoje = formatadorDataDeHoje.format(new Date());
		
		LogValor logValor = new LogValor();
		float valor = valorGrupoSalvo.getValorAtual();
		
		logValor.setValorNovo(valor);
		logValor.setData(dataDeHoje);
		
		logValor.setDescricao(valorGrupoSalvo.getProcessos().getDescricao() + "/"+ valorGrupoSalvo.getLinha().getDescricao() + "/" + valorGrupoSalvo.getTipoProduto().getDescricao());
		logValor.setValorGrupo(valorGrupoSalvo);
		logValorRepository.save(logValor);
		return ResponseEntity.status(HttpStatus.OK).body(valorGrupoSalvo);
	}

	

	@GetMapping()
	protected ResponseEntity<List<ValorGrupo>> listar() {
		List<ValorGrupo> valorGrupo = valorGrupoRepository.findAll();

		if (valorGrupo.get(0).getId() == 0) {
			return ResponseEntity.noContent().build();
		}

		return !valorGrupo.isEmpty() ? ResponseEntity.ok(valorGrupo) : ResponseEntity.noContent().build();
	}

	@GetMapping("/lastID")
	protected ResponseEntity<?> pegarUltimoID() {
		ValorGrupo valorGrupo = valorGrupoRepository.findTopByOrderByIdDesc();
		if (valorGrupo != null)
			return ResponseEntity.ok(valorGrupo.getId() + 1);
		else
			return ResponseEntity.ok(1);

	}
	
	

	@GetMapping("/{id}")
	public ResponseEntity<List<ValorGrupo>> listarTodosOsValorGrupoDeUmProcesso(@PathVariable("id") Long id){
		List<ValorGrupo> listValorGrupo = valorGrupoRepository.findByProcessoId(id);
		return !listValorGrupo.isEmpty() ? ResponseEntity.ok(listValorGrupo) : ResponseEntity.noContent().build();
	
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteValorGrupo(@PathVariable Long id) {
		valorGrupoRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ValorGrupo> atualizaValorGrupo(@PathVariable("id") Long id,@RequestBody ValorGrupo valorGrupo, HttpServletResponse responseEntity) {
		ValorGrupo valorGrupoSalvo = valorGrupoRepository.save(valorGrupo);
		LogValor logValor = new LogValor();
		  
		SimpleDateFormat formatadorDataDeHoje = new SimpleDateFormat("dd/MM/yyyy");
		String dataDeHoje = formatadorDataDeHoje.format(new Date());
		 
		float valor = valorGrupoSalvo.getValorAtual();

		logValor.setValorNovo(valor);
		logValor.setData(dataDeHoje);
		logValorRepository.save(logValor);
		
		
		return valorGrupoRepository.findById(id).map(record -> {
			record.setValorAtual(valorGrupo.getValorAtual());
			ValorGrupo updated = valorGrupoRepository.save(record);
			return ResponseEntity.ok().body(updated);

		}).orElse(ResponseEntity.notFound().build());
	}
}
