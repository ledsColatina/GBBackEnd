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
import com.example.BackEnd.domain.LogValor;
import com.example.BackEnd.domain.ValorGrupo;
import com.example.BackEnd.repository.LinhaRepository;
import com.example.BackEnd.repository.LogValorRepository;
import com.example.BackEnd.repository.ValorGrupoRepository;

@RestController
@RequestMapping(value = "/valorgrupo")
public class ValorGrupoResource {

	@Autowired
	private ValorGrupoRepository valorGrupoRepository;

	@Autowired
	private LogValorRepository logValorRepository;
	
	@Autowired
	private LinhaRepository linhaRepository;

	//----------------------------------------------------------------------------------------------------------------------
	
	@PostMapping
	protected ResponseEntity<ValorGrupo> criarValorGrupo(@Valid @RequestBody ValorGrupo valorGrupo,HttpServletResponse responseEntity) {
		ValorGrupo valorGrupoSalvo = valorGrupoRepository.save(valorGrupo);
		
		LogValor logValor = new LogValor();
		float valor = valorGrupoSalvo.getValorAtual();
		
		logValor.setValorNovo(valor);
		logValor.setData(new java.util.Date(System.currentTimeMillis()));
		
		logValor.setDescricao(valorGrupoSalvo.getSubProcesso().getDescricao() + "/"+ valorGrupoSalvo.getLinha().getDescricao() + "/" + valorGrupoSalvo.getTipoProduto().getDescricao());
		//System.out.println(valorGrupoSalvo.getSubProcesso().getDescricao() + "/"+ valorGrupoSalvo.getLinha().getDescricao() + "/" + valorGrupoSalvo.getTipoProduto().getDescricao());
		//logValor.setValorGrupo(valorGrupoSalvo);
		logValor.setStatus("Adicionado");
		logValorRepository.save(logValor);
		return ResponseEntity.status(HttpStatus.OK).body(valorGrupoSalvo);
	}

	//----------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------
	@GetMapping
	public ResponseEntity<List<ValorGrupo>> listar(){
		List<ValorGrupo> valorGrupo = valorGrupoRepository.findAll();	
		return !valorGrupo.isEmpty() ? ResponseEntity.ok(valorGrupo) : ResponseEntity.noContent().build();	
	}
	
	//----------------------------------------------------------------------------------------------------------------------

	@GetMapping("/{id}")
	public ResponseEntity<List<ValorGrupo>> listarTodosOsValorGrupoDeUmProcesso(@PathVariable("id") Long id){
		List<ValorGrupo> listValorGrupo = valorGrupoRepository.findBySubProcessoId(id);
		return !listValorGrupo.isEmpty() ? ResponseEntity.ok(listValorGrupo) : ResponseEntity.noContent().build();
	
	}
	
	
	//----------------------------------------------------------------------------------------------------------------------

		@GetMapping("/ordenavalorgrupo")
		public ResponseEntity<List<ValorGrupo>> ordenarValorGrupo(){
			List<ValorGrupo> listValorGrupo = valorGrupoRepository.findAllByOrderByLinhaDescricaoAscTipoProdutoDescricaoAsc();
			return !listValorGrupo.isEmpty() ? ResponseEntity.ok(listValorGrupo) : ResponseEntity.noContent().build();
		}
		
	//----------------------------------------------------------------------------------------------------------------------

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteValorGrupo(@PathVariable Long id) {
		valorGrupoRepository.deleteById(id);
	}

	
	//----------------------------------------------------------------------------------------------------------------------
	
	@PutMapping("/{id}")
	public ResponseEntity<ValorGrupo> atualizaValorGrupo(@PathVariable("id") Long id,@RequestBody ValorGrupo valorGrupo) {
		LogValor logValor = new LogValor();
		float valor = valorGrupo.getValorAtual();

		
		
		return valorGrupoRepository.findById(id).map(record -> {
			record.setValorAtual(valorGrupo.getValorAtual());
			ValorGrupo ValorGrupoALterado = valorGrupoRepository.save(record);
			
			logValor.setValorNovo(valor);
			logValor.setData(new java.util.Date(System.currentTimeMillis()));
			logValor.setDescricao(ValorGrupoALterado.getSubProcesso().getDescricao() + "/"+ ValorGrupoALterado.getLinha().getDescricao() + "/" + ValorGrupoALterado.getTipoProduto().getDescricao());
			//logValor.setValorGrupo(ValorGrupoALterado);
			logValor.setStatus("Alterado");
			logValorRepository.save(logValor);
			return ResponseEntity.ok().body(ValorGrupoALterado);

		}).orElse(ResponseEntity.notFound().build());
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	
	@PutMapping("/alterarlista")
	public ResponseEntity<ValorGrupo> atualizaListaValorGrupo(@RequestBody List<ValorGrupo> listValorGrupo, HttpServletResponse responseEntity) {
		for(int i=0;i<listValorGrupo.size();i++) {
			atualizaValorGrupo(listValorGrupo.get(i));
		}
	}
}
