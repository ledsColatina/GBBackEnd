package com.example.BackEnd.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity

public class ValorGrupo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private float valorAtual;
	
	
	
	@ManyToOne
	@JoinColumn(name = "linha_id")
	private Linha linha;
	
	
	@ManyToOne
	@JoinColumn(name = "tipoProduto_id")
	private TipoProduto tipoProduto;
	
	
	@ManyToOne
	@JoinColumn(name = "SubProcesso_id")
	private SubProcesso subProcesso;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getValorAtual() {
		return valorAtual;
	}

	public void setValorAtual(float valorAtual) {
		this.valorAtual = valorAtual;
	}

	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public SubProcesso getSubProcesso() {
		return subProcesso;
	}

	public void setSubProcesso(SubProcesso subProcesso) {
		this.subProcesso = subProcesso;
	}

	
	
	

	

}
