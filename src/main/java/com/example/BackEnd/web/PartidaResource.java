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
import com.example.BackEnd.domain.OrdemProducao;
import com.example.BackEnd.domain.Partida;
import com.example.BackEnd.dto.PartidaDTO;
import com.example.BackEnd.repository.EtapaProducaoRepository;
import com.example.BackEnd.repository.PartidaRepository;
import com.example.BackEnd.service.PartidaService;

@RestController
@RequestMapping(value = "/partida")
public class PartidaResource {
	
	@Autowired
    private PartidaRepository partidaRepository;
	
	@Autowired
    private PartidaService partidaService;
	
	@Autowired
    private EtapaProducaoRepository etapaProducaoRepository;
	
	//----------------------------------------------------------------------------------------------------------------------
	@GetMapping
	public ResponseEntity<List<Partida>> listarPartidas(){
		List<Partida> partida = partidaRepository.findAll();	
		return !partida.isEmpty() ? ResponseEntity.ok(partida) : ResponseEntity.noContent().build();	
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	@GetMapping("/{id}")
	public ResponseEntity<?> buscaPartida(@PathVariable("id") Long id){
		Optional<Partida> partida = partidaRepository.findById(id);	
		return ResponseEntity.ok(partida);
	}
		
	//----------------------------------------------------------------------------------------------------------------------
	@GetMapping("/inicio")
	public ResponseEntity<List<PartidaDTO>> inicioPartidas() { 	
		return ResponseEntity.ok(partidaService.consultar());	
	}
	
	//----------------------------------------------------------------------------------------------------------------------
		@GetMapping("/fim")
		public ResponseEntity<List<PartidaDTO>> finalizarPartidas() { 	
			return ResponseEntity.ok(partidaService.buscarPartidasIniciadas());	
		}
		
	//----------------------------------------------------------------------------------------------------------------------
	
    @PostMapping
    public ResponseEntity<Partida> criarPartida(@Valid @RequestBody  Partida partida){
    	Partida partidaSalva = partidaRepository.save(partida);
    	return ResponseEntity.status(HttpStatus.OK).body(partidaSalva);
    }
	
	//----------------------------------------------------------------------------------------------------------------------
	
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePartida(@PathVariable Long id){
    	partidaRepository.deleteById(id);
    }
  		
	//----------------------------------------------------------------------------------------------------------------------
	

    @PutMapping("/{id}") 
    public ResponseEntity<?> atualizaPartidaInicio(@PathVariable("id") Long id,@RequestBody Partida partida,HttpServletResponse responseEntity){
    	Optional<Partida> partidaPesquisada = partidaRepository.findById(id);
    	Partida novaPartida = null ;
    	if(partida.getQuantidade() < partidaPesquisada.get().getQuantidade()) {
    		novaPartida = new Partida();
    		//novaPartida.setDataFim(dataFim);
    		//novaPartida.setDataInicio(partida.getDataInicio());
    		novaPartida.setEtapaProducao(partidaPesquisada.get().getEtapaProducao());
    		//novaPartida.setHoraFim(horaFim);
    		//novaPartida.setHoraInicio(partida.getHoraInicio());
    		novaPartida.setMaquina(partidaPesquisada.get().getMaquina());
    		novaPartida.setStatus(partidaPesquisada.get().getStatus());
    		novaPartida.setQuantidade(partidaPesquisada.get().getQuantidade() - partida.getQuantidade());
    		Partida partidaSalva = partidaRepository.save(novaPartida);
    		System.out.println(partida.getStatus());
        	System.out.println(partidaSalva.getStatus());
    	}
    	
    	
    	
    	 Optional<Object> partidaAlterada = partidaRepository.findById(id).map(record -> {
			    		record.setDataInicio(partida.getDataInicio());
			    		record.setHoraInicio(partida.getHoraInicio());
			    		record.setQuantidade(partida.getQuantidade());
			    		record.setStatus(partida.getStatus());
			    		record.setMaquina(partida.getMaquina());
			    		Partida updated = partidaRepository.save(record);
    	                return updated;                  	               
    	 });
    	 
    	 //----------
    	 Optional<EtapaProducao> etapa  = etapaProducaoRepository.findById(partida.getEtapaProducao().getId());
    	 if(partida.getStatus().equals("iniciada") ) {
    		 etapa.get().setQtdEmEspera(etapa.get().getQtdEmEspera() - partida.getQuantidade());
    		 etapa.get().setQtdEmProducao(etapa.get().getQtdEmProducao() + partida.getQuantidade());
    		 System.out.println("INICIADA");
    	 }
    	 if(partida.getStatus().equals("finalizada")){
    		 etapa.get().setQtdEmProducao(etapa.get().getQtdEmProducao() - partida.getQuantidade());
    		 etapa.get().setQtdFinalizado(etapa.get().getQtdFinalizado() + partida.getQuantidade());
    		 System.out.println("FINALIZADA");
    	 }
    	
    	 etapaProducaoRepository.findById(etapa.get().getId()).map(record -> {
	    		record.setFimPrevisto(etapa.get().getFimPrevisto());
	    		record.setInicioPrevisto(etapa.get().getInicioPrevisto());
	    		record.setProcesso(etapa.get().getProcesso());
	    		record.setListSubProcesso(etapa.get().getListSubProcesso());
	    		record.setQtdEmEspera(etapa.get().getQtdEmEspera());
	    		record.setQtdEmProducao(etapa.get().getQtdEmProducao());
	    		record.setQtdFinalizado(etapa.get().getQtdFinalizado());
	    		record.setSequencia(etapa.get().getSequencia());
	    		EtapaProducao updated = etapaProducaoRepository.save(record);
             return ResponseEntity.ok().body(updated);
                	               
        }).orElse(ResponseEntity.notFound().build());
    	 
    	 
    	 return ResponseEntity.ok(etapa);
    }   
	//----------------------------------------------------------------------------------------------------------------------
	
	//----------------------------------------------------------------------------------------------------------------------
}
