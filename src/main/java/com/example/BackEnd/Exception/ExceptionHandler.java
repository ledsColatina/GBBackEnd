package com.example.BackEnd.Exception;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler{
		//dsfdsfg
	@Autowired
	private MessageSource messageSource;
	
	protected ResponseEntity<Object> handlerHttpMessageNotReadable(HttpMessageNotReadableException ex,
										HttpHeaders headers,HttpStatus status,WebRequest request){
		
		String mensagemUsuario = messageSource.getMessage("mensagem.invalida",null,LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getMessage().toString();
		return handleExceptionInternal(ex,new Erro(mensagemUsuario,mensagemDesenvolvedor), headers, HttpStatus.BAD_REQUEST, request);
	}
	
	
@org.springframework.web.bind.annotation.ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex ,WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado",null,LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List <Erro> erros = Arrays.asList(new Erro(mensagemUsuario,mensagemDesenvolvedor));
		return handleExceptionInternal(ex,erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	
	
	public static class Erro {
		String mensagemUsuario;
		String mensagemDesenvolvedor;
		
		
		public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}


		public String getMensagemUsuario() {
			return mensagemUsuario;
		}


		public void setMensagemUsuario(String mensagemUsuario) {
			this.mensagemUsuario = mensagemUsuario;
		}


		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}


		public void setMensagemDesenvolvedor(String mensagemDesenvolvedor) {
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}
		
		
	}
}

