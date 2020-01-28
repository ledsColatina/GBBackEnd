/*
package com.example.BackEnd.config;




import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;





import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	
//--------------------------------------------------------------------------------------------------------
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("admin").password("{noop}admin").roles("ROLE");
	}
	
//--------------------------------------------------------------------------------------------------------	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().authorizeRequests()
				.antMatchers("/cliente").permitAll()
				.anyRequest().authenticated()
				.and()
			.httpBasic().and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.csrf().disable();
	}
	
//--------------------------------------------------------------------------------------------------------	
	@Override
	public void configure (WebSecurity web)throws Exception {
		web.ignoring().antMatchers("/cliente");
		web.ignoring().antMatchers("/cliente/*");
		web.ignoring().antMatchers(HttpMethod.OPTIONS ).antMatchers("/oauth/token");
		web.ignoring().antMatchers("/setor");
		web.ignoring().antMatchers("/setor/*");
		web.ignoring().antMatchers("/actuator/**");
		
	}
	
	
	
}
*/