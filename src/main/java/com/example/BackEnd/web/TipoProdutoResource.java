package com.example.BackEnd.web;

import java.util.ArrayList;
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

import com.example.BackEnd.domain.CapacidadeProducao;
import com.example.BackEnd.domain.Linha;
import com.example.BackEnd.domain.Maquina;
import com.example.BackEnd.domain.SubProcesso;
import com.example.BackEnd.domain.TipoProduto;
import com.example.BackEnd.domain.ValorGrupo;
import com.example.BackEnd.repository.CapacidadeProducaoRepository;
import com.example.BackEnd.repository.LinhaRepository;
import com.example.BackEnd.repository.MaquinaRepository;
import com.example.BackEnd.repository.SubProcessosRepository;
import com.example.BackEnd.repository.TipoProdutoRepository;
import com.example.BackEnd.repository.ValorGrupoRepository;

@RestController
@RequestMapping(value = "/tipoproduto")
public class TipoProdutoResource {
	
	@Autowired
	private TipoProdutoRepository tipoProdutoRepository;
	
	@Autowired
	private MaquinaRepository maquinaRepository;
	
	@Autowired
	private CapacidadeProducaoRepository capacidadeProducaoRepository;
	
	@Autowired
	private SubProcessosRepository subProcessosRepository;
	
	@Autowired
	private LinhaRepository linhaRepository;
	
	@Autowired
	private ValorGrupoRepository valorGrupoRepository;

	//----------------------------------------------------------------------------------------------------------------------
	
	@GetMapping
	protected ResponseEntity<List<TipoProduto>> listar(){
		List<TipoProduto> tipoProduto = tipoProdutoRepository.findAll();
		return !tipoProduto.isEmpty() ? ResponseEntity.ok(tipoProduto) : ResponseEntity.noContent().build();
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	
	@GetMapping("/lastID")
	protected ResponseEntity<?> pegarUltimoID(){
		TipoProduto tipoProduto = tipoProdutoRepository.findTopByOrderByIdDesc();
		if (tipoProduto != null)
			return ResponseEntity.ok(tipoProduto.getId()+1);
		else
			return ResponseEntity.ok(1);

	}
	
	//----------------------------------------------------------------------------------------------------------------------
	
	@PostMapping
    protected ResponseEntity<TipoProduto> criarTipoProduto(@Valid @RequestBody  TipoProduto tipoProduto,HttpServletResponse responseEntity){		
		TipoProduto tipoProdutoSalvo = tipoProdutoRepository.save(tipoProduto);
		
		List<SubProcesso> listSubProcesso = subProcessosRepository.findAll();
		List<Linha> listLinha = linhaRepository.findAll();
		
		List<Maquina> listmaquina = maquinaRepository.findAll();
		CapacidadeProducao CapacidadeProducao;
		ValorGrupo valorGrupoNovo;
		
		for(int i=0;i<listmaquina.size();i++) {
			CapacidadeProducao = new CapacidadeProducao();
			CapacidadeProducao.setMaquina(listmaquina.get(i));
			CapacidadeProducao.setTipoProduto(tipoProdutoSalvo);
			CapacidadeProducao.setCapacidadeHora(0);
			capacidadeProducaoRepository.save(CapacidadeProducao);
		}
		
		
		
		
		
		
		for(int k=0;k<listLinha.size();k++) {
					
			for(int j=0;j<listSubProcesso.size();j++) {
				valorGrupoNovo = new ValorGrupo();
				valorGrupoNovo.setLinha(listLinha.get(k));
				valorGrupoNovo.setTipoProduto(tipoProdutoSalvo);
				valorGrupoNovo.setSubProcesso(listSubProcesso.get(j));
				valorGrupoNovo.setValorAtual(0);
				valorGrupoRepository.save(valorGrupoNovo);
			}
		}

    	return ResponseEntity.status(HttpStatus.OK).body(tipoProdutoSalvo);
    }
	
	//----------------------------------------------------------------------------------------------------------------------
	
	@DeleteMapping("/{id}")	
    @ResponseStatus(HttpStatus.NO_CONTENT)
    protected void deleteTipoProduto(@PathVariable Long id){
		tipoProdutoRepository.deleteById(id);
    }
	
	//----------------------------------------------------------------------------------------------------------------------
	
	@PutMapping("/{id}")
    protected ResponseEntity<TipoProduto> atualizaTipoProduto(@PathVariable("id") Long id,@RequestBody TipoProduto tipoProduto,HttpServletResponse responseEntity){
		return tipoProdutoRepository.findById(id).map(record -> {
			    		record.setDescricao(tipoProduto.getDescricao());
			    		TipoProduto updated = tipoProdutoRepository.save(record);
    	                return ResponseEntity.ok().body(updated); 	               
    	           }).orElse(ResponseEntity.notFound().build());
    }  
	
	//----------------------------------------------------------------------------------------------------------------------
}
