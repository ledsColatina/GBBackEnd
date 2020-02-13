package com.example.BackEnd.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
//REMOVER IMPORTS DESNECESSARIO
@Entity
public class Processos {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String descricao;
	
	
	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "processos_id")
	private List<ValorGrupo>listValorGrupo;
	
	
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

	public List<ValorGrupo> getListValorGrupo() {
		return listValorGrupo;
	}

	public void setListValorGrupo(List<ValorGrupo> listValorGrupo) {
		this.listValorGrupo = listValorGrupo;
	}
	
}
