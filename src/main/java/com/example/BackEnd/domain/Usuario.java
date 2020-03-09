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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Nome do Usuario deve ser informado")
	@NotNull(message = "Nome do Usuario deve ser informado")
	private String nome;
	private String login;
	private String senha;
	private Boolean tipo; 
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_setor", joinColumns = { @JoinColumn(name = "usuario_id") }, inverseJoinColumns = {
			@JoinColumn(name = "setor_id") })
	private List<Setor> setores;
	
}
