package com.example.BackEnd.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.example.BackEnd.service.exception.RegraDeNegocioException;
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
import com.example.BackEnd.domain.LogValor;
import com.example.BackEnd.domain.OrdemProducao;
import com.example.BackEnd.domain.SubProcesso;
import com.example.BackEnd.domain.TipoProduto;
import com.example.BackEnd.domain.ValorGrupo;
import com.example.BackEnd.repository.LinhaRepository;
import com.example.BackEnd.repository.LogValorRepository;
import com.example.BackEnd.repository.OrdemProducaoRepository;
import com.example.BackEnd.repository.SubProcessosRepository;
import com.example.BackEnd.repository.TipoProdutoRepository;
import com.example.BackEnd.repository.ValorGrupoRepository;

@RestController
@RequestMapping(value = "/linha")
public class LinhaResource {

	@Autowired
	private LinhaRepository linhaRepository;
	
	@Autowired
	private TipoProdutoRepository tipoProdutoRepository;
	
	@Autowired
	private SubProcessosRepository subProcessosRepository;
	
	@Autowired
	private ValorGrupoRepository valorGrupoRepository;
	
	@Autowired
    private OrdemProducaoRepository ordemProducaoRepository;
	
	@Autowired
	private LogValorRepository logValorRepository;

	//----------------------------------------------------------------------------------------------------------------------------
	
	@GetMapping
	protected ResponseEntity<List<Linha>> listar() {
		List<Linha> linha = linhaRepository.findAll();
		return !linha.isEmpty() ? ResponseEntity.ok(linha) : ResponseEntity.noContent().build();
	}

	//----------------------------------------------------------------------------------------------------------------------------
	
	@PostMapping
	protected ResponseEntity<?> criarLinha(@Valid @RequestBody Linha linha, HttpServletResponse responseEntity) {
		Linha linhaSalvo = linhaRepository.save(linha);
		
		List<TipoProduto> listTipoProd = tipoProdutoRepository.findAll();
		List<SubProcesso> listSubProcesso = subProcessosRepository.findAll();
		
		ValorGrupo valorGrupoNovo;
		
		if(listTipoProd.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		if(listSubProcesso.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		
		
		for(int i=0;i<listTipoProd.size();i++) {
			
			for(int j=0;j<listSubProcesso.size();j++) {
				valorGrupoNovo = new ValorGrupo();
				valorGrupoNovo.setLinha(linhaSalvo);
				valorGrupoNovo.setTipoProduto(listTipoProd.get(i));
				valorGrupoNovo.setSubProcesso(listSubProcesso.get(j));
				valorGrupoNovo.setValorAtual(0);
				valorGrupoRepository.save(valorGrupoNovo);
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(linhaSalvo);
	}

	//----------------------------------------------------------------------------------------------------------------------------
	
	@DeleteMapping("/{id}")
	protected ResponseEntity<Void> deleteLinha(@PathVariable Long id) {
		List<ValorGrupo> ListValorGrupoExcluido = valorGrupoRepository.findByLinhaId(id);
		LogValor logValor;
		List<OrdemProducao> listOrdemProducao = ordemProducaoRepository.findByLinhaId(id);

		if(!listOrdemProducao.isEmpty()) {
			throw new RegraDeNegocioException("Linha esta sendo usada em um OP");
		}else {
			if(!ListValorGrupoExcluido.isEmpty()){
				valorGrupoRepository.deleteByLinhaId(id);
				for(int i=0;i<ListValorGrupoExcluido.size();i++) {
					logValor = new LogValor();
					logValor.setData(new java.util.Date(System.currentTimeMillis()));
					logValor.setDescricao("SubProcesso: " + ListValorGrupoExcluido.get(i).getSubProcesso().getDescricao() + "\n"+
							"Linha: " + ListValorGrupoExcluido.get(i).getLinha().getDescricao() + "\n" +
							"TipoProduto: " + ListValorGrupoExcluido.get(i).getTipoProduto().getDescricao());
					logValor.setStatus("Excluido");
					logValorRepository.save(logValor);
				}
			}
			
			linhaRepository.deleteById(id);
		}
		return ResponseEntity.ok(null);
	}

	//----------------------------------------------------------------------------------------------------------------------------
	
	@PutMapping("/{id}")
	protected ResponseEntity<Linha> atualizaLinha(@PathVariable("id") Long id, @RequestBody Linha linha,
			HttpServletResponse responseEntity) {
		return linhaRepository.findById(id).map(record -> {
			record.setDescricao(linha.getDescricao());
			Linha updated = linhaRepository.save(record);
			return ResponseEntity.ok().body(updated);

		}).orElse(ResponseEntity.notFound().build());
	}
	
	//----------------------------------------------------------------------------------------------------------------------------
}
