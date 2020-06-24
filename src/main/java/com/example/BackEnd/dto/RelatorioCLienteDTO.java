package com.example.BackEnd.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioCLienteDTO {
	private String nomeCliente;
	private List<ListRelatorioProducaoPorClienteDTO> listRelatorio;
	private int total;
	
}
