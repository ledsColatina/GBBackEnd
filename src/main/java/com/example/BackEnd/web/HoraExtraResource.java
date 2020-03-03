package com.example.BackEnd.web;

import java.text.ParseException;
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
import com.example.BackEnd.domain.HoraExtra;
import com.example.BackEnd.repository.HoraExtraRepository;


@RestController
@RequestMapping(value = "/horaExtra")
public class HoraExtraResource {

	@Autowired
	private HoraExtraRepository horaExtraRepository;

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
	
	@GetMapping("/{id}/finalizados")
	public ResponseEntity<List<HoraExtra>> listarHoraExtraFinalizadasPorSetor(@PathVariable("id") Long id) {
		List<HoraExtra> horaExtra = horaExtraRepository.findAllFinalizadas(id);
		return ResponseEntity.ok(horaExtra);
	}

	//----------------------------------------------------------------------------------------------------------------------------
	
	@GetMapping("/{id}/pendentes")
	public ResponseEntity<List<HoraExtra>> listarHoraExtraPendentesPorSetor(@PathVariable("id") Long id) {
		List<HoraExtra> horaExtra = horaExtraRepository.findAllPendentes(id);
		return ResponseEntity.ok(horaExtra);
	}

	//----------------------------------------------------------------------------------------------------------------------------
	
	@PostMapping
	public ResponseEntity<?> criarHoraExtra(@Valid @RequestBody HoraExtra horaExtra, HttpServletResponse responseEntity)throws ParseException {
		HoraExtra horaExtraSalva = horaExtraRepository.save(horaExtra);
		return ResponseEntity.status(HttpStatus.OK).body(horaExtraSalva);
	}

	//----------------------------------------------------------------------------------------------------------------------------
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteHoraExtra(@PathVariable Long id) {
		horaExtraRepository.deleteById(id);
	}
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	@PutMapping("/{id}") 
    public ResponseEntity<HoraExtra> atualizaHoraExtra(@PathVariable("id") Long id,@RequestBody HoraExtra horaExtra,HttpServletResponse responseEntity){
    	return horaExtraRepository.findById(id).map(record -> {
			    		record.setCapacidade(horaExtra.getCapacidade());;
			    		record.setData(horaExtra.getData());
			    		record.setTurnoFunciona(horaExtra.isTurnoFunciona());
			    		record.setQtdHoras(horaExtra.getQtdHoras());
			    		record.setStatus(horaExtra.getStatus());
			    		HoraExtra updated = horaExtraRepository.save(record);
    	                return ResponseEntity.ok().body(updated);
    	                   	               
    	           }).orElse(ResponseEntity.notFound().build());
    }  
	
	//----------------------------------------------------------------------------------------------------------------------------
}
