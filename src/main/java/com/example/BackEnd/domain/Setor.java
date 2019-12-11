package com.example.BackEnd.domain;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;



@Entity
public class Setor {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Nome do Setor deve ser informado")
	@NotNull(message = "Nome do Setor deve ser informado")
	private String nome;
	
	
	private Long capacidade;
	
	
	private Long diasDeOcupacao;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Long capacidade) {
		this.capacidade = capacidade;
	}

	public Long getDiasDeOcupacao() {
		return diasDeOcupacao;
	}

	public void setDiasDeOcupacao(Long DiasDeOcupacao) {
		this.diasDeOcupacao = DiasDeOcupacao;
	}
	
	
}
