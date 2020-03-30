package com.example.BackEnd.domain;



import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class HoraExtra {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	private String data;
	private float qtdHoras;
	private String momento;
	private String status;
	private String horaInicio;
	private String horaFim;

	
	@ManyToOne
	@JoinColumn(name = "turno")
	private Turno turno;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "horaExtra_maquina", joinColumns = { @JoinColumn(name = "horaExtra_id") }, inverseJoinColumns = {
			@JoinColumn(name = "maquina_id") }) 
	private List<Maquina> listaMaquina;


}
