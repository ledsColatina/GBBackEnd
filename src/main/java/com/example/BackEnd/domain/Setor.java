package com.example.BackEnd.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Setor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Nome do Setor deve ser informado")
	@NotNull(message = "Nome do Setor deve ser informado")
	private String nome;

	private Long maxOcupacao;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "setor_id")
	private List<Turno> ListTurno;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "setor_id")
	private List<Processos> ListProcessos;

	public List<Turno> getListTurno() {
		return ListTurno;
	}

	public void setListTurno(List<Turno> listTurno) {
		ListTurno = listTurno;
	}

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

	public Long getMaxOcupacao() {
		return maxOcupacao;
	}

	public void setMaxOcupacao(Long maxOcupacao) {
		this.maxOcupacao = maxOcupacao;
	}

	public List<Processos> getListProcessos() {
		return ListProcessos;
	}

	public void setListProcessos(List<Processos> listProcessos) {
		ListProcessos = listProcessos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Setor other = (Setor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
