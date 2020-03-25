package com.example.BackEnd.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Maquina {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Nome do Setor deve ser informado")
	//@NotNull(message = "Nome do Setor deve ser informado")
	private String nome;
	private Long maxOcupacao;
	private String role;

	//@OneToMany(cascade = { CascadeType.ALL})
	//@JoinColumn(name = "setor_id")
	//private List<Turno> ListTurno;
	
	
	

	
}
