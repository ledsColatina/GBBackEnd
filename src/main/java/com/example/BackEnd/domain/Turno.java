package com.example.BackEnd.domain;

import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Turno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricaoTurno;
	private String horaInicio;
	private int totalHoras;
	
	private ArrayList<String> diasDaSemana;

	// @ManyToOne
	// @JoinColumn(name = "setor_id")
	// private Setor setor;
	
}
