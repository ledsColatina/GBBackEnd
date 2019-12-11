package com.example.BackEnd.web;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackEnd.domain.Usuario;
import com.example.BackEnd.repository.UsuarioRepository;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioResource {
	private UsuarioRepository usuarioRepasitory;
	
	@PostMapping()
    protected ResponseEntity<Usuario> criarLinha(@Valid @RequestBody  Usuario usuario,HttpServletResponse responseEntity){
		Usuario usuarioSalvo = usuarioRepasitory.save(usuario);
    	return ResponseEntity.status(HttpStatus.OK).body(usuarioSalvo);
    }
}
