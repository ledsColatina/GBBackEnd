package com.example.BackEnd.domain;


import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;


@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", length = 1, discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("P")
public class CapacidadeProducao {
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
