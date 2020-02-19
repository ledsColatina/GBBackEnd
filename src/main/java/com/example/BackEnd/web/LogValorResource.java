package com.example.BackEnd.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackEnd.domain.LogValor;
import com.example.BackEnd.repository.LogValorRepository;

@RestController
@RequestMapping(value = "processos/logValor")
public class LogValorResource {
	
	@Autowired
	private LogValorRepository logValorRepository;
	
	
	@PostMapping()
    protected ResponseEntity<LogValor> criarLogValor(@Valid @RequestBody  LogValor logValor,HttpServletResponse responseEntity){
		LogValor logValorSalvo = logValorRepository.save(logValor);
		//System.out.println(logValorSalvo.getId());
    	return ResponseEntity.status(HttpStatus.OK).body(logValorSalvo);
    }
	
	@GetMapping()
	protected ResponseEntity<List<LogValor>> listarLogValor(){
		List<LogValor> logValor = logValorRepository.OrderByIdDesc();
		return !logValor.isEmpty() ? ResponseEntity.ok(logValor) : ResponseEntity.noContent().build();
	}
}
