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
public class HoraExtraTipoProduto {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private int capacidade;
	
	@ManyToOne
	@JoinColumn(name = "horaExtra_id")
	private HoraExtra horaExtra;
	
	@ManyToOne
	@JoinColumn(name = "tipoProduto_id")
	private TipoProduto tipoProduto;
}
