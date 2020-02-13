package com.example.BackEnd.domain;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class LogValor {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	// USAR DOUBLE
	private float ValorNovo;

	// @DateTimeFormat(pattern = "dd/MM/yyyy")
	private String data;

	// @ManyToOne
	// @JoinColumn(name = "valorGrupo_id")
	// private ValorGrupo valorGrupo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public float getValorNovo() {
		return ValorNovo;
	}

	public void setValorNovo(float valorNovo) {
		ValorNovo = valorNovo;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
