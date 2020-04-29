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

import com.example.BackEnd.domain.Cliente;
import com.example.BackEnd.domain.EtapaProducao;
import com.example.BackEnd.domain.ObjetoPesquisaValorGrupo;
import com.example.BackEnd.domain.OrdemProducao;
import com.example.BackEnd.domain.Partida;
import com.example.BackEnd.domain.SubProcesso;
import com.example.BackEnd.domain.ValorGrupo;
import com.example.BackEnd.repository.EtapaProducaoRepository;
import com.example.BackEnd.repository.OrdemProducaoRepository;
import com.example.BackEnd.repository.PartidaRepository;
import com.example.BackEnd.repository.ValorGrupoRepository;

@RestController
@RequestMapping(value = "/ordemproducao")
public class OrdemProducaoResource {
	
	@Autowired
    private OrdemProducaoRepository ordemProducaoRepository;
	
	@Autowired
    private EtapaProducaoRepository etapaProducaoRepository;
	
	@Autowired
    private ValorGrupoRepository valorGrupoRespository;
	
	@Autowired
    private PartidaRepository partidaRepository;
	
	//----------------------------------------------------------------------------------------------------------------------
	
		@GetMapping
		public ResponseEntity<List<OrdemProducao>> listar(){
			List<OrdemProducao> ordemProducao = ordemProducaoRepository.findAll();	
			return !ordemProducao.isEmpty() ? ResponseEntity.ok(ordemProducao) : ResponseEntity.noContent().build();	
		}
	
		
		//----------------------------------------------------------------------------------------------------------------------
		
			@GetMapping("/{id}")
			public ResponseEntity<Optional<OrdemProducao>> listarPorId(@PathVariable("id") Long id){
				Optional<OrdemProducao> ordemProducao = ordemProducaoRepository.findById(id);
				return ResponseEntity.ok(ordemProducao);	
			}
		
	//----------------------------------------------------------------------------------------------------------------------
		
		@PostMapping("/valortotal")
		public float listarValores(@Valid @RequestBody ObjetoPesquisaValorGrupo ObjetoPesquisaValorGrupo,HttpServletResponse responseEntity){
			List<SubProcesso> listSubProcessos = ObjetoPesquisaValorGrupo.getListSubProcesso();
			float total=0;
			
			for(int i=0;i<listSubProcessos.size();i++) {
				ValorGrupo valorGrupo = new ValorGrupo();
				valorGrupo = valorGrupoRespository.findByLinhaIdAndTipoProdutoIdAndSubProcessoId(
						ObjetoPesquisaValorGrupo.getLinha().getId(),ObjetoPesquisaValorGrupo.getTipoProduto().getId(),
						listSubProcessos.get(i).getId());
			
				total = total + valorGrupo.getValorAtual();
			}
			
				
			return total;
		}
		
	//----------------------------------------------------------------------------------------------------------------------

		@PostMapping
	    public ResponseEntity<OrdemProducao> criarOrdemProducao(@Valid @RequestBody  OrdemProducao ordemProducao,HttpServletResponse responseEntity){
			//List<EtapaProducao> listEtapas = ordemProducao.getListEtapas();
			//for(int i=0;i<listEtapas.size();i++) {
			//	etapaProducaoRepository.save(listEtapas.get(i));
			//}
			OrdemProducao ordemProducaoSalva = ordemProducaoRepository.save(ordemProducao);
			
			List<EtapaProducao> listEtapas = ordemProducaoSalva.getListEtapas();
			Partida partida;
			for(EtapaProducao listE: listEtapas) {
				partida = new Partida();
				partida.setEtapaProducao(listE);
				partida.setQuantidade(ordemProducaoSalva.getQuantidade());
				partidaRepository.save(partida);
	        }
			
				
			
	    	return ResponseEntity.status(HttpStatus.OK).body(ordemProducaoSalva);
	    }
		
	//----------------------------------------------------------------------------------------------------------------------
		
		 @DeleteMapping("/{id}")
		    @ResponseStatus(HttpStatus.NO_CONTENT)
		    public void deleteOrdemProducao(@PathVariable Long id){
			 ordemProducaoRepository.deleteById(id);
		    }
		
	//----------------------------------------------------------------------------------------------------------------------
		 @PutMapping("/{id}") 
		    public ResponseEntity<OrdemProducao> atualizaOrdemProducao(@PathVariable("id") Long id,@RequestBody OrdemProducao ordemProducao,HttpServletResponse responseEntity){
		    	return ordemProducaoRepository.findById(id).map(record -> {
					    		record.setCliente(ordemProducao.getCliente());
					    		record.setDataEmissao(ordemProducao.getDataEmissao());
					    		record.setLinha(ordemProducao.getLinha());
					    		record.setListEtapas(ordemProducao.getListEtapas());
					    		record.setOrdemProducaoOriginal(ordemProducao.getOrdemProducaoOriginal());
					    		record.setPrioridadeAtual(ordemProducao.getPrioridadeAtual());
					    		record.setPrioridadeInicial(ordemProducao.getPrioridadeInicial());
					    		record.setQuantidade(ordemProducao.getQuantidade());
					    		record.setReferencia(ordemProducao.getReferencia());
					    		record.setReprocesso(ordemProducao.isReprocesso());
					    		record.setTipoProduto(ordemProducao.getTipoProduto());
					    		record.setValorTotal(ordemProducao.getValorTotal());
					    		OrdemProducao updated = ordemProducaoRepository.save(record);
		    	                return ResponseEntity.ok().body(updated);
		    	                   	               
		    	           }).orElse(ResponseEntity.notFound().build());
		    }   
}
