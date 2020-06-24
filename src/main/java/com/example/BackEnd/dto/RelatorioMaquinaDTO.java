package com.example.BackEnd.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioMaquinaDTO {
	private String nomeMaquina;
	private List<ListRelatorioProducaoPorMaquinaDTO> listRelatorio;
	private int total;
	private double ocupacaoEmDias;
}
