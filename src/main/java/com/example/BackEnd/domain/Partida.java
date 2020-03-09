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

@Entity
@Data
public class Partida {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date dataInicio;
	private Time horaInicio;
	private Date dataFim;
	private Long horaFim;
	private Long Quantidade;

	@ManyToOne
	@JoinColumn(name = "etapaOrigem_id")
	private EtapaProducao origem;

	@ManyToOne
	@JoinColumn(name = "etapaDestino_id")
	private EtapaProducao Destino;

}
