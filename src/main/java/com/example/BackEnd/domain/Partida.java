package com.example.BackEnd.domain;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data

public class Partida {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String dataInicio;
	private String horaInicio;
	private String dataFim;
	private String horaFim;
	private int quantidade;
	private String status;
	
	
	@ManyToOne
	@JoinColumn(name = "etapaProducao_id")
	private EtapaProducao etapaProducao;

	@ManyToOne
	@JoinColumn(name = "maquina_id")
	private Maquina maquina;

}
