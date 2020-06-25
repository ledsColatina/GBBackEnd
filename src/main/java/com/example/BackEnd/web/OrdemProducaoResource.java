package com.example.BackEnd.web;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.example.BackEnd.service.GanttService;
import com.example.BackEnd.service.OrdemProducaoService;
import lombok.RequiredArgsConstructor;
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
import com.example.BackEnd.dto.PartidaDTO;
import com.example.BackEnd.repository.EtapaProducaoRepository;
import com.example.BackEnd.repository.OrdemProducaoRepository;
import com.example.BackEnd.repository.PartidaRepository;
import com.example.BackEnd.repository.ProcessoRepository;
import com.example.BackEnd.repository.ValorGrupoRepository;
import com.example.BackEnd.service.PartidaService;

@RestController
@RequestMapping(value = "/ordemproducao")
@RequiredArgsConstructor
public class OrdemProducaoResource {

	private final GanttService ganttService;

    private final OrdemProducaoRepository ordemProducaoRepository;

    private final OrdemProducaoService ordemProducaoService;

    private final EtapaProducaoRepository etapaProducaoRepository;

    private final ValorGrupoRepository valorGrupoRespository;

    private final PartidaRepository partidaRepository;

    private final ProcessoRepository ProcessoRepository;
	
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
	    public ResponseEntity<?> criarOrdemProducao(@Valid @RequestBody  OrdemProducao ordemProducao,HttpServletResponse responseEntity) throws Exception {
			OrdemProducao ordemProducaoSalva = ordemProducaoRepository.save(ordemProducao);
			
			List<EtapaProducao> listEtapas = ordemProducaoSalva.getListEtapas();
			Partida partida;
			for(EtapaProducao etapa: listEtapas) {
				System.out.println(listEtapas.get(0).getSequencia());
				partida = new Partida();
				partida.setEtapaProducao(etapa);
				partida.setStatus("pendente"); //iniciada,finalizada
				partida.setMaquina(etapa.getProcesso().getListaMaquina().get(0));
				partida.setQuantidade(ordemProducaoSalva.getQuantidade());
				partidaRepository.save(partida);
	        }

			ganttService.preencherTarefas(ordemProducaoSalva.getId());

	    	return ResponseEntity.status(HttpStatus.OK).body(ordemProducaoSalva);
	    }

	    @GetMapping("/referencia/{ref}")
		public ResponseEntity<OrdemProducao> buscarPorReferencia(@PathVariable("ref") String ref){
			OrdemProducao op = ordemProducaoService.buscarPorReferencia(ref);
			return ResponseEntity.ok(op);
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
		    	//EtapaProducao etapaOriginal = etapaProducaoRepository.findById(ordemProducao.getListEtapas())
			 
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
