package com.example.BackEnd.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
public class Tarefa {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Descrição da Tarefa deve ser informado")
	@NotNull(message = "Descrição da Tarefa deve ser informado")
	private String descricao;
	
	
	@ManyToOne
	@JoinColumn(name = "setor_id")
	private Setor setor;
	
	
	@ManyToMany(mappedBy = "tarefas")
	private List<EtapaProducao> estapasDeProducao;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
