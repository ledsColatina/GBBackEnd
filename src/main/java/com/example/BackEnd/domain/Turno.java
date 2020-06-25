package com.example.BackEnd.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Data
public class Turno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricaoTurno;
	private String horaInicio;
	private String horaFim;
	private ArrayList<String> diasDaSemana;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "maquina_turno", joinColumns = { @JoinColumn(name = "turno_id") },
			inverseJoinColumns = {@JoinColumn(name = "maquina_id") })
	private List<Maquina> listaMaquina;
}
