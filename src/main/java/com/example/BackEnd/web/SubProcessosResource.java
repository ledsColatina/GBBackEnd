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

import com.example.BackEnd.domain.Linha;
import com.example.BackEnd.domain.SubProcesso;
import com.example.BackEnd.domain.TipoProduto;
import com.example.BackEnd.domain.ValorGrupo;
import com.example.BackEnd.repository.LinhaRepository;
import com.example.BackEnd.repository.SubProcessosRepository;
import com.example.BackEnd.repository.TipoProdutoRepository;
import com.example.BackEnd.repository.ValorGrupoRepository;


@RestController
@RequestMapping(value = "/subprocesso")
public class SubProcessosResource {
	
	@Autowired
	private SubProcessosRepository subProcessosRepository;
	
	@Autowired
	private TipoProdutoRepository tipoProdutoRepository;
	
	@Autowired
	private LinhaRepository linhaRepository;
	
	@Autowired
	private ValorGrupoRepository valorGrupoRepository;
	
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	@PostMapping
    protected ResponseEntity<SubProcesso> criarSubProcesso(@Valid @RequestBody  SubProcesso subProcesso,HttpServletResponse responseEntity){
		SubProcesso subProcessoSalvo = subProcessosRepository.save(subProcesso);
		
		List<Linha> listLinha = linhaRepository.findAll();
		List<TipoProduto> listTipoProd = tipoProdutoRepository.findAll();
		ValorGrupo valorGrupoNovo;
		
		for(int k=0;k<listLinha.size();k++) {
			
			for(int j=0;j<listTipoProd.size();j++) {
				valorGrupoNovo = new ValorGrupo();
				valorGrupoNovo.setLinha(listLinha.get(k));
				valorGrupoNovo.setTipoProduto(listTipoProd.get(j));
				valorGrupoNovo.setSubProcesso(subProcesso);
				valorGrupoNovo.setValorAtual(0);
				valorGrupoRepository.save(valorGrupoNovo);
			}
		}
		
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
