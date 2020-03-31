package com.example.BackEnd.domain;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
@DiscriminatorValue(value = "F")
public class CapacidadeProducaoExtra extends CapacidadeProducao {
	

	public CapacidadeProducaoExtra() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@ManyToOne
	@JoinColumn(name = "horaExtra_id")
	private HoraExtra horaExtra;
}
