package com.example.BackEnd.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class PartidaGantt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "horario_id")
	private Horario horario;

	@ManyToOne
	@JoinColumn(name = "maquina_id")
	private Maquina maquina;

	@ManyToOne
	@JoinColumn(name = "partida_id")
	private Partida partida;

	private LocalDate dia;

	private Long inicio;

	private Long fim;

	private String cor;

	private String referencia;

	private Long quantidade;

	private Long quantidadeTotal;
}
