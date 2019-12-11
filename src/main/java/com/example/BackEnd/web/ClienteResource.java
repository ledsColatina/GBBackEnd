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

import com.example.BackEnd.domain.Cliente;

import com.example.BackEnd.repository.ClienteRepository;


@RestController
@RequestMapping(value = "/cliente")
public class ClienteResource {
	
	@Autowired
    private ClienteRepository clienteRepository;
	
	//@CrossOrigin(origins = "http://172.16.36.14:9098")
	@GetMapping
	public ResponseEntity<List<Cliente>> listar(){
		List<Cliente> cliente = clienteRepository.findAll();	
		return !cliente.isEmpty() ? ResponseEntity.ok(cliente) : ResponseEntity.noContent().build();	
	}
	/*
	@GetMapping("/{cor}")
	protected ResponseEntity<List<Cliente>> listarCor(@PathVariable String cor){
		List<Cliente> cliente = clienteRepository.findByCor(cor);
		if(cliente == null) 
			throw new ResourceNotFoundException("Cliente não encontrado pela a COR " + cor);
		
		return new ResponseEntity<>(cliente,HttpStatus.OK);
	}*/
	/*
	@GetMapping("/{id}")
	protected ResponseEntity<?> listarPorId(@PathVariable Long id){
		Optional<Cliente> cliente  = clienteRepository.findById(id);
		return  cliente.isPresent() ? ResponseEntity.ok(cliente) : ResponseEntity.noContent().build();
		
	}*/
	
	@GetMapping("/lastID")
	public ResponseEntity<?>  pegarUltimoID(){
		Cliente cliente = clienteRepository.findTopByOrderByIdDesc();
		if (cliente != null)
			return ResponseEntity.ok(cliente.getId()+1);
		else
			return ResponseEntity.ok(1);
	}
	
    @PostMapping()
    public ResponseEntity<Cliente> criarCliente(@Valid @RequestBody  Cliente cliente,HttpServletResponse responseEntity){
    	Cliente clienteSalvo = clienteRepository.save(cliente);
    	return ResponseEntity.status(HttpStatus.OK).body(clienteSalvo);
    }
	
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCliente(@PathVariable Long id){
    	clienteRepository.deleteById(id);
    }
    
    @PutMapping("/{id}") 
    public ResponseEntity<Cliente> atualizaCliente(@PathVariable("id") Long id,@RequestBody Cliente cliente,HttpServletResponse responseEntity){
    	return clienteRepository.findById(id).map(record -> {
			    		record.setNome(cliente.getNome());
			    		record.setCor(cliente.getCor());
			    		Cliente updated = clienteRepository.save(record);
    	                return ResponseEntity.ok().body(updated);
    	                   	               
    	           }).orElse(ResponseEntity.notFound().build());
    }    
}

