/*
package com.example.BackEnd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

//-----------------------------------------------------------------------------------------------------------------
	@Override
	//Configurar a aplicação(Cliente)Altorizar o cliente a usar o authorizationServer
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
		//PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		clients.inMemory()
			.withClient("leds")			//Nome do cliente
			.secret("leds29700")	//Senha do cliente
			.scopes("read","write")		//Qual é o escopo do nosso cliente(Com esse escopo pode se limitar o acesso de determinado cliente)
			.authorizedGrantTypes("password") //fluxo onde a aplicação recebe o usuario e senha do usuario e emviar para pegar o acess token
			.accessTokenValiditySeconds(1800); //30min
		
	}

	
//-----------------------------------------------------------------------------------------------------------------	
	@Override
	//Preciso armazenar o token em algum lugar pois a aplicação vira buscar um token pra poder acessar a API de verdade(/setor de exemplo)
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)throws Exception{
		endpoints
			.tokenStore(tokenStore())
			.authenticationManager(authenticationManager);
	}
	
//-----------------------------------------------------------------------------------------------------------------
	//lugar onde vou armazenar o token
	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}
	
	
	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
*/