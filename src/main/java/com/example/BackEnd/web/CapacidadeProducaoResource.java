package com.example.BackEnd.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackEnd.domain.Cliente;
import com.example.BackEnd.domain.CapacidadeProducao;
import com.example.BackEnd.repository.CapacidadeProducaoRepository;

@RestController
@RequestMapping(value = "/capacidadeproducao")
public class CapacidadeProducaoResource {
	
	@Autowired
    private CapacidadeProducaoRepository maquinaTipoProdutoRepository;
	
	
	
	
	//-----------------------------------------------------------------------------------------------------------------------	
	
			@PutMapping("/{id}")
			protected ResponseEntity<CapacidadeProducao> atualizaCapacidade(@PathVariable("id") Long id,  @RequestBody CapacidadeProducao maquinaTipoProduto,HttpServletResponse responseEntity) {
				return maquinaTipoProdutoRepository.findById(id).map(record -> {
					record.setCapacidadeHora(maquinaTipoProduto.getCapacidadeHora());
					CapacidadeProducao updated = maquinaTipoProdutoRepository.save(record);
					return ResponseEntity.ok().body(updated);

				}).orElse(ResponseEntity.notFound().build());
			}
			
	//--------------------------------------------------------------------------------------------------		
		
			
			@GetMapping
			public ResponseEntity<List<CapacidadeProducao>> listar(){
				List<CapacidadeProducao> maquinaTipoProduto = maquinaTipoProdutoRepository.findAll();	
				return !maquinaTipoProduto.isEmpty() ? ResponseEntity.ok(maquinaTipoProduto) : ResponseEntity.noContent().build();	
			}
			
	//-------------------------------------------------------------------------------------------------------------
			
	  @GetMapping("/{id}")
			public ResponseEntity<List<CapacidadeProducao>> pegarCapacidadeDeCadaMaquina(@PathVariable Long id) {
				List<CapacidadeProducao> mtp = maquinaTipoProdutoRepository.findByMaquinaId(id);
				return ResponseEntity.status(HttpStatus.OK).body(mtp);
			}
			
	  //-------------------------------------------------------------------------------------------------------------------			
			
	  
	    @DeleteMapping("/{id}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void deleteMaquinaTipoProduto(@PathVariable Long id){
	    	maquinaTipoProdutoRepository.deleteById(id);
	    }
	   
}
