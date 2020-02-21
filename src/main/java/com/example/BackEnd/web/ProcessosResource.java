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
import com.example.BackEnd.domain.Processos;
import com.example.BackEnd.repository.ProcessosRepository;


@RestController
@RequestMapping(value = "/processos")
public class ProcessosResource {
	
	@Autowired
	private ProcessosRepository processosRepository;
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	@PostMapping
    protected ResponseEntity<Processos> criarProcesso(@Valid @RequestBody  Processos processos,HttpServletResponse responseEntity){
		Processos processosSalvo = processosRepository.save(processos);
		System.out.println(processosSalvo.getId());
    	return ResponseEntity.status(HttpStatus.OK).body(processosSalvo);
    }
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	@GetMapping("/setor/{id}")
	protected ResponseEntity<List<Processos>> listarProcessosPorSetor(@PathVariable("id") Long id){
		List<Processos> processos = processosRepository.findAllProcessosDoSetor(id);
		return !processos.isEmpty() ? ResponseEntity.ok(processos) : ResponseEntity.noContent().build();
	}
	
	//----------------------------------------------------------------------------------------------------------------------------
	@GetMapping
	protected ResponseEntity<List<Processos>> listarProcessos(){
		List<Processos> processos = processosRepository.findAll();
		return !processos.isEmpty() ? ResponseEntity.ok(processos) : ResponseEntity.noContent().build();
	}
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	@PutMapping("/{id}") 
    public ResponseEntity<Processos> atualizaProcessos(@PathVariable("id") Long id,@RequestBody Processos processos,HttpServletResponse responseEntity){
    	return processosRepository.findById(id).map(record -> {
			    		record.setDescricao(processos.getDescricao());
			    		Processos updated = processosRepository.save(record);
    	                return ResponseEntity.ok().body(updated);
    	                   	               
    	           }).orElse(ResponseEntity.notFound().build());
    }   
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	@DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProcessos(@PathVariable Long id){
			processosRepository.deleteById(id);
    }
	
	//----------------------------------------------------------------------------------------------------------------------------
	
}
