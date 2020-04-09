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
import com.example.BackEnd.domain.EtapaProducao;
import com.example.BackEnd.repository.EtapaProducaoRepository;

@RestController
@RequestMapping(value = "/etapaproducao")
public class EtapaProducaoResource {


	@Autowired
    private EtapaProducaoRepository etapaProducaoRepository;
	
	//------------------------------------------------------------------------------------------------------------------
	
	@GetMapping
	public ResponseEntity<List<EtapaProducao>> listar(){
		List<EtapaProducao> etapaProducao = etapaProducaoRepository.findAll();	
		return !etapaProducao.isEmpty() ? ResponseEntity.ok(etapaProducao) : ResponseEntity.noContent().build();	
	}
	
	//------------------------------------------------------------------------------------------------------------------
	
	
    @PostMapping
    public ResponseEntity<EtapaProducao> criarEtapaProducao(@Valid @RequestBody  EtapaProducao etapaProducao,HttpServletResponse responseEntity){
    	EtapaProducao etapaProducaoSalva = etapaProducaoRepository.save(etapaProducao);
    	return ResponseEntity.status(HttpStatus.OK).body(etapaProducaoSalva);
    }
    
    //------------------------------------------------------------------------------------------------------------------
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEtapaProducao(@PathVariable Long id){
    	etapaProducaoRepository.deleteById(id);
    }
    
    //------------------------------------------------------------------------------------------------------------------
    
    @PutMapping("/{id}") 
    public ResponseEntity<EtapaProducao> atualizaEtapaProducao(@PathVariable("id") Long id,@RequestBody EtapaProducao etapaProducao,HttpServletResponse responseEntity){
    	return etapaProducaoRepository.findById(id).map(record -> {
			    		record.setFimPrevisto(etapaProducao.getFimPrevisto());
			    		record.setInicioPrevisto(etapaProducao.getInicioPrevisto());
			    		record.setProcesso(etapaProducao.getProcesso());
			    		record.setListSubProcesso(etapaProducao.getListSubProcesso());
			    		record.setQtdEmEspera(etapaProducao.getQtdEmEspera());
			    		record.setQtdEmProducao(etapaProducao.getQtdEmProducao());
			    		record.setQtdFinalizado(etapaProducao.getQtdFinalizado());
			    		record.setSequencia(etapaProducao.getSequencia());
			    		EtapaProducao updated = etapaProducaoRepository.save(record);
    	                return ResponseEntity.ok().body(updated);
    	                   	               
    	           }).orElse(ResponseEntity.notFound().build());
    }  
    
}
