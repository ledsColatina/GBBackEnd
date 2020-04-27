
  package com.example.BackEnd.web;
 
  
  import java.util.List;
 
  import javax.servlet.http.HttpServletResponse; 
  import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus; 
  import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.example.BackEnd.domain.Usuario; 
  import com.example.BackEnd.repository.UsuarioRepository;
  
  @RestController
  @RequestMapping(value = "/usuario") 
  public class UsuarioResource { 
	  
	  @Autowired
	  private UsuarioRepository usuarioRepository;
	  
	
		
	  
	  //----------------------------------------------------------------------------------------------------------------------
	 
	  @PostMapping
	  protected ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody Usuario usuario,HttpServletResponse responseEntity) throws Exception{ 
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
  
	//----------------------------------------------------------------------------------------------------------------------------    
	    
	    @DeleteMapping("/{id}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void deleteUsuario(@PathVariable Long id){
	    	usuarioRepository.deleteById(id);
	    }
	   
	    //----------------------------------------------------------------------------------------------------------------------------   
	    
	    @PutMapping("/{id}") 
	    public ResponseEntity<Usuario> atualizaUsuario(@PathVariable("id") Long id,@RequestBody Usuario usuario,HttpServletResponse responseEntity){
	    	return usuarioRepository.findById(id).map(record -> {
				    		record.setLogin(usuario.getLogin());
				    		record.setMaquinas(usuario.getMaquinas());
				    		record.setNome(usuario.getNome());
				    		record.setSenha(usuario.getSenha());
				    		record.setTipo(usuario.getTipo());
				    		Usuario updated = usuarioRepository.save(record);
	    	                return ResponseEntity.ok().body(updated);
	    	                   	               
	    	           }).orElse(ResponseEntity.notFound().build());
	    }   
	  //----------------------------------------------------------------------------------------------------------------------------
  }
 