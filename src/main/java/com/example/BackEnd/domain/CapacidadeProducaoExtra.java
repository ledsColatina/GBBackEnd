package com.example.BackEnd.domain;



import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
//@DiscriminatorValue(value = "F")
@Getter
@Setter
public class CapacidadeProducaoExtra extends CapacidadeProducao {
	

	public CapacidadeProducaoExtra() {
		super();
	}
	
	@ManyToOne
	@JoinColumn(name = "horaExtra_id")
	private HoraExtra horaExtra;
}
