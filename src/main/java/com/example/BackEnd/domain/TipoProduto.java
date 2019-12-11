package com.example.BackEnd.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.persistence.Entity;

@Entity
public class TipoProduto {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Grupo(Promo,Line) do produto deve ser informado")
	@NotNull(message = "Grupo(Promo,Line) do produto deve ser informado")
	private String descricaoTipoProduto;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricaoTipoProduto() {
		return descricaoTipoProduto;
	}

	public void setDescricaoTipoProduto(String grupo) {
		this.descricaoTipoProduto = grupo;
	}
	
	
}
