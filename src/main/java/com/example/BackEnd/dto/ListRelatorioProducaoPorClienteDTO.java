package com.example.BackEnd.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListRelatorioProducaoPorClienteDTO {

	private Long idOrdem;
	private String refCliente;
	private int quantidade;
	private int prioridade;
	private String dataSaida;
	
}
