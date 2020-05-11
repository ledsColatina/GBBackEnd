package com.example.BackEnd.dto;

import java.util.List;

import com.example.BackEnd.domain.Maquina;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartidaDTO {
	private Long idPartida;//-
	private String referenciaOP;
	private int sequenciaEtapa;//-
	private String nomeCliente;
	private String nomeMaquina;
	private int quantidadePartida;//-
	private List<Maquina> listMaquinasUsuario;
	
	

}
