package com.example.BackEnd.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackEnd.domain.MaquinaTipoProduto;
import com.example.BackEnd.repository.MaquinaTipoProdutoRepository;

@RestController
@RequestMapping(value = "/maquinatipoproduto")
public class MaquinaTipoProdutoResource {
	
	@Autowired
    private MaquinaTipoProdutoRepository maquinaTipoProdutoRepository;
	
	
	
	
	//-----------------------------------------------------------------------------------------------------------------------	
	
			@PutMapping("/capacidade")
			protected ResponseEntity<MaquinaTipoProduto> atualizaCapacidade(@PathVariable  @RequestBody MaquinaTipoProduto maquinaTipoProduto,HttpServletResponse responseEntity) {
				return maquinaTipoProdutoRepository.findByChaveComposta(maquinaTipoProduto).map(record -> {
					record.setCapacidadeHora(maquinaTipoProduto.getCapacidadeHora());
					
					MaquinaTipoProduto updated = maquinaTipoProdutoRepository.save(record);
					return ResponseEntity.ok().body(updated);

				}).orElse(ResponseEntity.notFound().build());
			}
			
}
