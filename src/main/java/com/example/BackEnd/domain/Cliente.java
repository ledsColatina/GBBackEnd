package com.example.BackEnd.domain;

//import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Nome do Cliente deve ser informado")
	//@NotNull(message = "Nome do Cliente deve ser informado")
	private String nome;

	@NotEmpty(message = "Cor do Cliente deve ser informado")
	//@NotNull(message = "Nome do Cliente deve ser informado")
	private String cor;

	// @OneToMany
	// private List<OrdemProducao> OrdensDeProducao;

}
