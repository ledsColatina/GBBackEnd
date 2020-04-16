package com.example.BackEnd.web;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
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
import com.example.BackEnd.domain.CapacidadeProducaoExtra;
import com.example.BackEnd.domain.HoraExtra;
import com.example.BackEnd.domain.Maquina;

import com.example.BackEnd.domain.TipoProduto;
import com.example.BackEnd.repository.HoraExtraRepository;
import com.example.BackEnd.repository.MaquinaRepository;
import com.example.BackEnd.repository.TipoProdutoRepository;
import com.example.BackEnd.repository.CapacidadeProducaoExtraRepository;
import com.example.BackEnd.repository.CapacidadeProducaoRepository;


@RestController
@RequestMapping(value = "/horaextra")
public class HoraExtraResource {

	@Autowired
	private HoraExtraRepository horaExtraRepository;
	
	
	@Autowired
    private com.example.BackEnd.repository.CapacidadeProducaoExtraRepository capacidadeProducaoExtraRepository;
	
	@Autowired
	private TipoProdutoRepository tipoProdutoRepository;
	
	@Autowired
	private MaquinaRepository maquinaRepository;
	
	@Autowired
	private CapacidadeProducaoRepository capacidadeProducaoRepository;
	
	//----------------------------------------------------------------------------------------------------------------------------   
	@GetMapping
	public ResponseEntity<List<HoraExtra>> listar() {
		List<HoraExtra> horaExtra = horaExtraRepository.findAll();
		return !horaExtra.isEmpty() ? ResponseEntity.ok(horaExtra) : ResponseEntity.noContent().build();
	}

	//----------------------------------------------------------------------------------------------------------------------------   
	
	@GetMapping("/setor/{id}")
	public ResponseEntity<List<HoraExtra>> HoraExtraPorSetor(@PathVariable("id") Long id) {
		List<HoraExtra> list = horaExtraRepository.findAllHoraExtraPorSetor(id);
		return ResponseEntity.ok(list);
	}
	
	//----------------------------------------------------------------------------------------------------------------------------   
	
		@GetMapping("/pendentes")
		public ResponseEntity<List<HoraExtra>> HoraExtraPendentes() {
			List<HoraExtra> list = horaExtraRepository.findAllPendentes();
			return ResponseEntity.ok(list);
		}
		
		
		//----------------------------------------------------------------------------------------------------------------------------   
		
			@GetMapping("/finalizadas")
			public ResponseEntity<List<HoraExtra>> HoraExtraFinalizadas() {
				List<HoraExtra> list = horaExtraRepository.findAllFinalizadas();
				return ResponseEntity.ok(list);
			}
			
	//----------------------------------------------------------------------------------------------------------------------------   
			
	@GetMapping("/capacidade/{id}")
		public ResponseEntity<?> CapacidadeHoraExtra(@PathVariable("id") Long id) {
			List<CapacidadeProducaoExtra> ListCapacidade = capacidadeProducaoExtraRepository.findByHoraExtraId(id);
			for(int k = 0;k<ListCapacidade.size();k++) {
				if(ListCapacidade.get(k) instanceof CapacidadeProducao) {
					return !ListCapacidade.isEmpty() ? ResponseEntity.ok(ListCapacidade) : ResponseEntity.noContent().build();
				}
			}	
			return ResponseEntity.ok("ok");
		}
			
						
	//----------------------------------------------------------------------------------------------------------------------------   
	
		@GetMapping("/{id}")
		public ResponseEntity<HoraExtra> HoraExtraPendentes(@PathVariable("id") Long id) {
			HoraExtra list = horaExtraRepository.findByPegarHoraExtra(id);
			return ResponseEntity.ok(list);
		}
		//sdfs
	
	//----------------------------------------------------------------------------------------------------------------------------
	
		@PostMapping
		public ResponseEntity<?> criarHoraExtra(@Valid @RequestBody HoraExtra horaExtra, HttpServletResponse responseEntity){
			HoraExtra horaExtraSalva = horaExtraRepository.save(horaExtra);
			List<TipoProduto> listTipoProd = tipoProdutoRepository.findAll();
			List<Maquina> listMaquina = horaExtraSalva.getListaMaquina();
			CapacidadeProducaoExtra capacidadeProducaoExtra;
			 
			
			for(int i = 0;i<listMaquina.size();i++) {
				for(int j = 0;j<listTipoProd.size();j++) {
					capacidadeProducaoExtra = new CapacidadeProducaoExtra();
					capacidadeProducaoExtra.setHoraExtra(horaExtraSalva);
					capacidadeProducaoExtra.setMaquina(listMaquina.get(i));
					capacidadeProducaoExtra.setTipoProduto(listTipoProd.get(j));
					List<CapacidadeProducao> listCapProducao = capacidadeProducaoRepository.findByMaquinaIdAndTipoProdutoId(listMaquina.get(i).getId(),listTipoProd.get(j).getId());
					for(int k = 0;k<listCapProducao.size();k++) {
						if(listCapProducao.get(k) instanceof CapacidadeProducao) {
							capacidadeProducaoExtra.setCapacidadeHora(listCapProducao.get(k).getCapacidadeHora());
							capacidadeProducaoExtraRepository.save(capacidadeProducaoExtra);
						}
					}	
				}
			}
			 
			
			
			return ResponseEntity.status(HttpStatus.OK).body(horaExtraSalva);
		}
	//----------------------------------------------------------------------------------------------------------------------------
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteHoraExtra(@PathVariable Long id) {
		List<CapacidadeProducaoExtra> listCapacidades = capacidadeProducaoExtraRepository.findByHoraExtraId(id);
		
		for(int i=0;i<listCapacidades.size();i++) {
			capacidadeProducaoRepository.deleteById(listCapacidades.get(i).getId());
		}
		capacidadeProducaoExtraRepository.deleteByHoraExtraId(id);
		
		horaExtraRepository.deleteById(id);
	}
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	@PutMapping("/{id}") 
    public ResponseEntity<HoraExtra> atualizaHoraExtra(@PathVariable("id") Long id,@RequestBody HoraExtra horaExtra,HttpServletResponse responseEntity){
    	return horaExtraRepository.findById(id).map(record -> {
			    		record.setHoraInicio(horaExtra.getHoraInicio());
			    		record.setData(horaExtra.getData());
			    		record.setHoraFim(horaExtra.getHoraFim());
			    		record.setQtdHoras(horaExtra.getQtdHoras());
			    		record.setStatus(horaExtra.getStatus());
			    		record.setMomento(horaExtra.getMomento());
			    		record.setTurno(horaExtra.getTurno());
			    		record.setListaMaquina(horaExtra.getListaMaquina());
			    		HoraExtra updated = horaExtraRepository.save(record);
    	                return ResponseEntity.ok().body(updated);
    	                   	               
    	           }).orElse(ResponseEntity.notFound().build());
    }  
	
	//----------------------------------------------------------------------------------------------------------------------------
}
