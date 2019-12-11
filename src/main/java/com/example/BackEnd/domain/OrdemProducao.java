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
public class OrdemProducao {
	// Somente Data
    //java.sql.Date date1;
    //@Temporal(TemporalType.DATE) java.util.Date date2
    //@Temporal(TemporalType.DATE) java.util.Calendar date3;
  
    // Somente hora
    //java.sql.Time time1;
    //@Temporal(TemporalType.TIME) java.util.Date time2;
    //@Temporal(TemporalType.TIME) java.util.Calendar time3;
  
    // Data e hora
    //java.sql.Timestamp dateAndTime1;
    //@Temporal(TemporalType.TIMESTAMP) java.util.Date dateAndTime2;
    //@Temporal(TemporalType.TIMESTAMP) java.util.Calendar dateAndTime3;
    //java.util.Date dateAndTime4; // date and time but not JPA portable
    //java.util.Calendar dateAndTime5; // date and time but not JPA portable 
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	private Long quantidade;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date emissao;
	
	private Long prioridade;
	
	private Long referencia;
	 
	private double valorTotal;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	
	@ManyToOne
	@JoinColumn(name = "tipoProduto_id")
	private TipoProduto tipoProduto;
	
	
	@ManyToOne
	@JoinColumn(name = "linha_id")
	private Linha linha;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public Date getEmissao() {
		return emissao;
	}

	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}

	public Long getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Long prioridade) {
		this.prioridade = prioridade;
	}

	public Long getReferencia() {
		return referencia;
	}

	public void setReferencia(Long referencia) {
		this.referencia = referencia;
	}


	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	
}
