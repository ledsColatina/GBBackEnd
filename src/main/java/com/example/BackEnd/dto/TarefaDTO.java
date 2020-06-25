package com.example.BackEnd.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TarefaDTO {
	private Long id;
	private Long idOP;
	private Long idEtapa;
	private Long idPartida;
	private Long prioridade;
}
