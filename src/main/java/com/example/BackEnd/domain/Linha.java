package com.example.BackEnd.domain;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Linha {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Descrição da Linha deve ser informado")
	@NotNull(message = "Descrição da Linha deve ser informado")
	private String descricaoLinha;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricaoLinha;
	}

	public void setDescricao(String descricao) {
		this.descricaoLinha = descricao;
	}
	
	
	
}
