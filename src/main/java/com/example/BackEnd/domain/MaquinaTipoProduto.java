package com.example.BackEnd.domain;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity
@Data
public class MaquinaTipoProduto {
	@EmbeddedId
	private MaquinaTipoProdutoPK chaveComposta;
	
	//@NotEmpty(message = "Capacidade de MaquinaTipoProduto deve ser informado")
	private int capacidadeHora;
}
