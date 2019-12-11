package com.example.BackEnd.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Nome do Usuario deve ser informado")
	@NotNull(message = "Nome do Usuario deve ser informado")
	private String nome;
	
	
	private Long senha;

	@ManyToMany()
	@JoinTable(name = "usuario_setor",
				joinColumns={@JoinColumn(name="usuario_id")},
				inverseJoinColumns={@JoinColumn(name="setor_id")}
	)
	private List<Setor> setores;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getSenha() {
		return senha;
	}

	public void setSenha(Long senha) {
		this.senha = senha;
	}
	
	
	
	
	
	
}
