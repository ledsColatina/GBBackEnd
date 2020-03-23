package com.example.BackEnd.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class MaquinaTipoProduto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	//@NotEmpty(message = "Capacidade de MaquinaTipoProduto deve ser informado")
	private int capacidadeHora;
	
	@ManyToOne
	@JoinColumn(name = "maquina_id")
	private Maquina maquina;
	
	@ManyToOne
	@JoinColumn(name = "tipoProduto_id")
	private TipoProduto tipoProduto;
}
