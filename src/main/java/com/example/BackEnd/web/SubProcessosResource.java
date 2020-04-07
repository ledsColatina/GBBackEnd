package com.example.BackEnd.web;


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
import com.example.BackEnd.domain.SubProcesso;
import com.example.BackEnd.repository.SubProcessosRepository;


@RestController
@RequestMapping(value = "/subprocesso")
public class SubProcessosResource {
	
	@Autowired
	private SubProcessosRepository subProcessosRepository;
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	@PostMapping
    protected ResponseEntity<SubProcesso> criarSubProcesso(@Valid @RequestBody  SubProcesso subProcesso,HttpServletResponse responseEntity){
		SubProcesso subProcessoSalvo = subProcessosRepository.save(subProcesso);
    	return ResponseEntity.status(HttpStatus.OK).body(subProcessoSalvo);
    }
	
	//----------------------------------------------------------------------------------------------------------------------------
	/*
	@GetMapping("/setor/{id}")
	protected ResponseEntity<List<SubProcesso>> listarProcessosPorSetor(@PathVariable("id") Long id){
		List<SubProcesso> processos = subProcessosRepository.findAllProcessosDoSetor(id);
		return !processos.isEmpty() ? ResponseEntity.ok(processos) : ResponseEntity.noContent().build();
	}
	*/
	//----------------------------------------------------------------------------------------------------------------------------
	@GetMapping
	protected ResponseEntity<List<SubProcesso>> listarSubProcessos(){
		List<SubProcesso> subProcessos = subProcessosRepository.findAll();
		return !subProcessos.isEmpty() ? ResponseEntity.ok(subProcessos) : ResponseEntity.noContent().build();
	}
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	@PutMapping("/{id}") 
    public ResponseEntity<SubProcesso> atualizaSubProcessos(@PathVariable("id") Long id,@RequestBody SubProcesso subProcessos,HttpServletResponse responseEntity){
    	return subProcessosRepository.findById(id).map(record -> {
			    		record.setDescricao(subProcessos.getDescricao());
			    		SubProcesso updated = subProcessosRepository.save(record);
    	                return ResponseEntity.ok().body(updated);
    	                   	               
    	           }).orElse(ResponseEntity.notFound().build());
    }   
	
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	@GetMapping("/processo/{id}")
	protected ResponseEntity<?> listarSubProcessosDoProcesso(@PathVariable("id") Long id){
		List<SubProcesso> SubProcessos = subProcessosRepository.findByProcessoId(id);
		return ResponseEntity.ok(SubProcessos);
	}
	
	//----------------------------------------------------------------------------------------------------------------------------
	
		@GetMapping("/{id}")
		protected ResponseEntity<?> PegarSubProcesso(@PathVariable("id") Long id){
			Optional<SubProcesso> SubProcessos = subProcessosRepository.findById(id);
			return ResponseEntity.ok(SubProcessos);
		}
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	@DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubProcessos(@PathVariable Long id){
		subProcessosRepository.deleteById(id);
    }
	
	//----------------------------------------------------------------------------------------------------------------------------
	
}
