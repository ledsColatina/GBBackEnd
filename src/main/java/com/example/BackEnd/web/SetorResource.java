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

import com.example.BackEnd.domain.Setor;
import com.example.BackEnd.repository.SetorRepository;

@RestController
@RequestMapping(value = "/setor")
public class SetorResource {
	
	@Autowired
    private SetorRepository setorRepository;
	
	@GetMapping()
	protected ResponseEntity<List<Setor>> listar(){
		List<Setor> setor = setorRepository.findAll();
		return !setor.isEmpty() ? ResponseEntity.ok(setor) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	protected ResponseEntity<?> listarPorId(@PathVariable Long id){
		Optional<Setor> setor  = setorRepository.findById(id);
		return  setor.isPresent() ? ResponseEntity.ok(setor) : ResponseEntity.noContent().build();
		
	}
	//testando commit
	@PostMapping()
    protected ResponseEntity<Setor> criarSetor(@Valid @RequestBody  Setor setor,HttpServletResponse responseEntity){
    	Setor setorSalvo = setorRepository.save(setor);
    	return ResponseEntity.status(HttpStatus.OK).body(setorSalvo);
    }
	
	@DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    protected ResponseEntity<?> deleteSetor(@PathVariable Long id){
		List<Setor> listaSetor = setorRepository.findAll();
		for(int i=0;i<listaSetor.size();i++) {
			if(listaSetor.get(i).getId() == id) {
				setorRepository.deleteById(id);
			}else  return ResponseEntity.ok("Setor nao encontrado para excluir!!");
		}
		
		
		return ResponseEntity.ok("ok");
		
    		
    		//return ResponseEntity.status(HttpStatus.OK).body(clienteExcluido);
    }
	
	@PutMapping("/{id}")
    protected ResponseEntity<Setor> atualizaCliente(@PathVariable("id") Long id,@RequestBody Setor setor,HttpServletResponse responseEntity){
    	return setorRepository.findById(id).map(record -> {
			    		record.setNome(setor.getNome());
			    		record.setCapacidade(setor.getCapacidade());
			    		record.setDiasDeOcupacao(setor.getDiasDeOcupacao());
			    		Setor updated = setorRepository.save(record);
    	                return ResponseEntity.ok().body(updated);
    	                   	               
    	           }).orElse(ResponseEntity.notFound().build());
    }    
}

