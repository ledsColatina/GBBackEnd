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

import com.example.BackEnd.domain.Processo;
import com.example.BackEnd.domain.SubProcesso;
import com.example.BackEnd.repository.ProcessoRepository;

@RestController
@RequestMapping(value = "/processo")
public class ProcessoResource {
	@Autowired
	private ProcessoRepository  processoRepository ;
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	@PostMapping
    protected ResponseEntity<Processo> criarProcesso(@Valid @RequestBody  Processo processo,HttpServletResponse responseEntity){
		Processo processoSalvo = processoRepository.save(processo);
    	return ResponseEntity.status(HttpStatus.OK).body(processoSalvo);
    }
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	@GetMapping("/{id}")
	protected ResponseEntity<?> listarProcessosProID(@PathVariable("id") Long id){
		Processo processos = processoRepository.findAllById(id);
		return ResponseEntity.ok(processos);
	}
	
	
		
	//----------------------------------------------------------------------------------------------------------------------------
	
	@GetMapping
	protected ResponseEntity<List<Processo>> listarProcessos(){
		List<Processo> processos = processoRepository.findAll();
		return !processos.isEmpty() ? ResponseEntity.ok(processos) : ResponseEntity.noContent().build();
	}
		
	//----------------------------------------------------------------------------------------------------------------------------
		
	@PutMapping("/{id}") 
	   public ResponseEntity<Processo> atualizaProcessos(@PathVariable("id") Long id,@RequestBody Processo processos,HttpServletResponse responseEntity){
	   	return processoRepository.findById(id).map(record -> {
			    		record.setDescricao(processos.getDescricao());
			    		record.setListMaquina(processos.getListMaquina());
			    		Processo updated = processoRepository.save(record);
	   	                return ResponseEntity.ok().body(updated);
    	                   	               
	   	           }).orElse(ResponseEntity.notFound().build());
	   }   
	
	//----------------------------------------------------------------------------------------------------------------------------
	
		@DeleteMapping("/{id}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void deleteProcessos(@PathVariable Long id){
			processoRepository.deleteById(id);
	    }
		
		//----------------------------------------------------------------------------------------------------------------------------	
}
