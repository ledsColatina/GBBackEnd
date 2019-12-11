package com.example.BackEnd.domain;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Turno {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	private Time horaInicio;
	private Time totalHoras;
	private Long capacidadeTurno;
	private Long descricao;
	
	@ManyToOne
	@JoinColumn(name = "setor_id")
	private Setor setor;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Time getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(Time horaInicio) {
		this.horaInicio = horaInicio;
	}
	public Time getTotalHoras() {
		return totalHoras;
	}
	public void setTotalHoras(Time totalHoras) {
		this.totalHoras = totalHoras;
	}
	public Long getCapacidadeTurno() {
		return capacidadeTurno;
	}
	public void setCapacidadeTurno(Long capacidadeTurno) {
		this.capacidadeTurno = capacidadeTurno;
	}
	public Long getDescrcao() {
		return descricao;
	}
	public void setDescrcao(Long descrcao) {
		this.descricao = descrcao;
	}
	
}
