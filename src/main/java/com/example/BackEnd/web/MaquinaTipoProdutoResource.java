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
import com.example.BackEnd.domain.MaquinaTipoProduto;
import com.example.BackEnd.repository.MaquinaTipoProdutoRepository;

@RestController
@RequestMapping(value = "/maquinatipoproduto")
public class MaquinaTipoProdutoResource {
	
	@Autowired
    private MaquinaTipoProdutoRepository maquinaTipoProdutoRepository;
	
	
	
	
	//-----------------------------------------------------------------------------------------------------------------------	
	
			@PutMapping("/{id}")
			protected ResponseEntity<MaquinaTipoProduto> atualizaCapacidade(@PathVariable("id") Long id,  @RequestBody MaquinaTipoProduto maquinaTipoProduto,HttpServletResponse responseEntity) {
				return maquinaTipoProdutoRepository.findById(id).map(record -> {
					record.setCapacidadeHora(maquinaTipoProduto.getCapacidadeHora());
					MaquinaTipoProduto updated = maquinaTipoProdutoRepository.save(record);
					return ResponseEntity.ok().body(updated);

				}).orElse(ResponseEntity.notFound().build());
			}
			
	//--------------------------------------------------------------------------------------------------		
		
			
			@GetMapping
			public ResponseEntity<List<MaquinaTipoProduto>> listar(){
				List<MaquinaTipoProduto> maquinaTipoProduto = maquinaTipoProdutoRepository.findAll();	
				return !maquinaTipoProduto.isEmpty() ? ResponseEntity.ok(maquinaTipoProduto) : ResponseEntity.noContent().build();	
			}
			
	//-------------------------------------------------------------------------------------------------------------
			
	  @GetMapping("/{id}")
			public ResponseEntity<List<MaquinaTipoProduto>> pegarCapacidadeDeCadaMaquina(@PathVariable Long id) {
				List<MaquinaTipoProduto> mtp = maquinaTipoProdutoRepository.findByMaquinaId(id);
				return ResponseEntity.status(HttpStatus.OK).body(mtp);
			}
			
//-------------------------------------------------------------------------------------------------------------------			
			
	  
	    @DeleteMapping("/{id}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void deleteMaquinaTipoProduto(@PathVariable Long id){
	    	maquinaTipoProdutoRepository.deleteById(id);
	    }
	   
}
