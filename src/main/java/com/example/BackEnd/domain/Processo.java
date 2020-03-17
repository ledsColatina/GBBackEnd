package com.example.BackEnd.domain;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Processo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String descricao;
	
	
	@OneToMany
	@JoinColumn(name = "setor_id")
	private List<Maquina> listSetores;
	
	

	
}
