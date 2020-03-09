package com.example.BackEnd.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class TipoProduto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Descricao do tipoProduto deve ser informado")
	@NotNull(message = "Descricao do tipoProduto deve ser informado")
	private String descricao;

	
}
