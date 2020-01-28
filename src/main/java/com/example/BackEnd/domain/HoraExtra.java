package com.example.BackEnd.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class HoraExtra {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	private Date data;
	
	private Long capacidade;
	
	private String qtdHoras;
	
	@ManyToOne
	@JoinColumn(name = "turno_id")
	private Turno turno;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Long getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Long capacidade) {
		this.capacidade = capacidade;
	}

	public String getQtdHoras() {
		return qtdHoras;
	}

	public void setQtdHoras(String qtdHoras) {
		this.qtdHoras = qtdHoras;
	}
	
	
}
