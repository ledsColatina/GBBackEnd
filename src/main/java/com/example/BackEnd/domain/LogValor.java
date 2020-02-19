package com.example.BackEnd.domain;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity

public class LogValor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// USAR DOUBLE
	private float ValorNovo;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date data;
	private String descricao;

	 @ManyToOne
	 @JoinColumn(name = "valorGrupo_id")
	 private ValorGrupo valorGrupo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public float getValorNovo() {
		return ValorNovo;
	}

	public void setValorNovo(float valorNovo) {
		ValorNovo = valorNovo;
	}

	public Date getData() {
		return data;
	}

	public void setData(java.util.Date date) {
		this.data = date;
	}

	public ValorGrupo getValorGrupo() {
		return valorGrupo;
	}

	public void setValorGrupo(ValorGrupo valorGrupo) {
		this.valorGrupo = valorGrupo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
}
