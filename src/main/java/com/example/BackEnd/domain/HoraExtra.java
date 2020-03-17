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
public class HoraExtra {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	private String data;
	
	private Long capacidade;
	
	private float qtdHoras;
	
	private String status;
	
	private boolean turnoFunciona;
	
	private String momento;
	
	@ManyToOne
	@JoinColumn(name = "turno")
	private Turno turno;


}
