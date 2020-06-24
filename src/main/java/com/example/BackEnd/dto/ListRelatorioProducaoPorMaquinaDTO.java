package com.example.BackEnd.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListRelatorioProducaoPorMaquinaDTO {
	private Long idOrdem;
	private String refCliente;
	private String nomeCliente;
	private int quantidade;
	private int prioridade;
	private Date dataSaida;
}
