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
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	private float ValorNovo;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date data;

	@ManyToOne
	@JoinColumn(name = "valorGrupo_id")
	private ValorGrupo valorGrupo;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public float getNovoValor() {
		return ValorNovo;
	}


	public void setNovoValor(float novoValor) {
		this.ValorNovo = novoValor;
	}


	public Date getData() {
		return data;
	}


	public void setData(Date data) {
		this.data = data;
	}


	public ValorGrupo getValorGrupo() {
		return valorGrupo;
	}


	public void setValorGrupo(ValorGrupo valorGrupo) {
		this.valorGrupo = valorGrupo;
	}
	
	
	
	
}
