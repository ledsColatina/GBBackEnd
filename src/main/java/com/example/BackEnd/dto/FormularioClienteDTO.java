package com.example.BackEnd.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormularioClienteDTO {
	private Long idCliente;
	private String nomeCliente;
	private int totalPecas;
	private int totalOrdemProducoes;
	private int pecasConcluidas;
	private int pecasFaltando;
}
