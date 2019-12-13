package com.example.BackEnd.protocol;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class MyFilter extends OncePerRequestFilter{
	
	@Override
	public void destroy() {
		
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin","https://smartzune.herokuapp.com");
		response.addHeader("Access-Control-Allow-Methods"," POST, GET, OPTIONS, DELETE, PUT");
		response.addHeader("Access-Control-Allow-Credentials","true");
		response.addHeader("Access-Control-Allow-Headers",
							"content-type, x-gtw-module-base, x-gwt-permutation,clientid,longpush");
		filterChain.doFilter(request, response);
	}
}

