
package com.example.BackEnd.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackEnd.domain.Usuario;
import com.example.BackEnd.repository.UsuarioRepository;


@Service
public class AppUserDetailsServer implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	//----------------------------------------------------------------------------------------------------------------------
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(login);
		Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuario e/ou senha incorretos"));
		return new User(login,usuario.getSenha(),getPermissoes(usuario));
	}

	//----------------------------------------------------------------------------------------------------------------------

	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		if(usuario.getTipo() == true) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));	
			authorities.add(new SimpleGrantedAuthority("ROLE_CHEFE"));	
		}else {
			authorities.add(new SimpleGrantedAuthority("ROLE_CHEFE"));	
		}
		return authorities;
	}
	
	//----------------------------------------------------------------------------------------------------------------------
}
