package com.example.BackEnd.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
public class OrdemProducao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long quantidade;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date emissao;

	private Long prioridade;

	private Long referencia;

	// trocar double por Double
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

	
}
