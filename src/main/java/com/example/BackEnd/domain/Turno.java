package com.example.BackEnd.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Turno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String descricaoTurno;
	private String horaInicio;
	private int totalHoras;
	private Long capacidadeTurno;

	// @ManyToOne
	// @JoinColumn(name = "setor_id")
	// private Setor setor;

	public String getDescricaoTurno() {
		return descricaoTurno;
	}

	public void setDescricaoTurno(String descricaoTurno) {
		this.descricaoTurno = descricaoTurno;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public int getTotalHoras() {
		return totalHoras;
	}

	public void setTotalHoras(int totalHoras) {
		this.totalHoras = totalHoras;
	}

	public Long getCapacidadeTurno() {
		return capacidadeTurno;
	}

	public void setCapacidadeTurno(Long capacidadeTurno) {
		this.capacidadeTurno = capacidadeTurno;
	}

}
