
  package com.example.BackEnd.web;
 
  
  import java.util.List;
 
  import javax.servlet.http.HttpServletResponse; 
  import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; 
  import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping; 
  import org.springframework.web.bind.annotation.PostMapping; 
  import org.springframework.web.bind.annotation.RequestBody; 
  import org.springframework.web.bind.annotation.RequestMapping;   
  import org.springframework.web.bind.annotation.RestController;
  import com.example.BackEnd.domain.Usuario; 
  import com.example.BackEnd.repository.UsuarioRepository;
  
  @RestController
  @RequestMapping(value = "/usuario") 
  public class UsuarioResource { 
	  
	  @Autowired
	  private UsuarioRepository usuarioRepository;
	  
	  //----------------------------------------------------------------------------------------------------------------------
	  
	  @PostMapping
	  protected ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody Usuario usuario,HttpServletResponse responseEntity){ 
		  BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();
		  usuario.setSenha(encoder.encode(usuario.getSenha()));
		  Usuario usuarioSalvo = usuarioRepository.save(usuario);
		  return ResponseEntity.status(HttpStatus.OK).body(usuarioSalvo); 
	  }
 
	  //----------------------------------------------------------------------------------------------------------------------
	  
	  @GetMapping 
	  public ResponseEntity<List<Usuario>> listar(){ 
		  List<Usuario> usuario = usuarioRepository.findAll(); 
		  return !usuario.isEmpty() ? ResponseEntity.ok(usuario) : ResponseEntity.noContent().build(); }
  
  }
 