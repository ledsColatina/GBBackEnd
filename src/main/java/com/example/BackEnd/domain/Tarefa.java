package com.example.BackEnd.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Tarefa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ordem_producao_id")
	private OrdemProducao op;

	@ManyToOne
	@JoinColumn(name = "etapa_producao_id")
	private EtapaProducao etapa;

	@ManyToOne
	@JoinColumn(name = "partida_id")
	private Partida partida;

	private Long prioridade;
}
