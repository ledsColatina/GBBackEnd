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
import com.example.BackEnd.domain.OrdemProducao;
import com.example.BackEnd.domain.Partida;
import com.example.BackEnd.dto.PartidaDTO;
import com.example.BackEnd.repository.PartidaRepository;
import com.example.BackEnd.service.PartidaService;

@RestController
@RequestMapping(value = "/partida")
public class PartidaResource {
	
	@Autowired
    private PartidaRepository partidaRepository;
	
	@Autowired
    private PartidaService partidaService;
	
	//----------------------------------------------------------------------------------------------------------------------
	@GetMapping
	public ResponseEntity<List<Partida>> listarPartidas(){
		List<Partida> partida = partidaRepository.findAll();	
		return !partida.isEmpty() ? ResponseEntity.ok(partida) : ResponseEntity.noContent().build();	
	}
	
	//----------------------------------------------------------------------------------------------------------------------
		@GetMapping("/inicio")
		public ResponseEntity<List<PartidaDTO>> inicoPartidas(OrdemProducao op){ 	
			return ResponseEntity.ok(partidaService.consultar(op));	
		}
		
	//----------------------------------------------------------------------------------------------------------------------
	
    @PostMapping
    public ResponseEntity<Partida> criarPartida(@Valid @RequestBody  Partida partida,HttpServletResponse responseEntity){
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
    public ResponseEntity<Partida> atualizaPartida(@PathVariable("id") Long id,@RequestBody Partida partida,HttpServletResponse responseEntity){
    	return partidaRepository.findById(id).map(record -> {
			    		record.setDataFim(partida.getDataFim());
			    		record.setDataInicio(partida.getDataInicio());
			    		record.setEtapaProducao(partida.getEtapaProducao());
			    		record.setHoraFim(partida.getHoraFim());
			    		record.setHoraInicio(partida.getHoraInicio());
			    		record.setMaquina(partida.getMaquina());
			    		record.setQuantidade(partida.getQuantidade());
			    		Partida updated = partidaRepository.save(record);
    	                return ResponseEntity.ok().body(updated);
    	                
    	                   	               
    	           }).orElse(ResponseEntity.notFound().build());
    }   
	//----------------------------------------------------------------------------------------------------------------------
	
	//----------------------------------------------------------------------------------------------------------------------
}
