package com.example.BackEnd.domain;

import java.util.List;

import javax.persistence.*;


import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
public class OrdemProducao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int quantidade;

	private String dataEmissao;
	private int prioridadeAtual;
	private String referencia;
	private boolean reprocesso;
	private double valorTotal;
	private int prioridadeInicial;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "etapaProducao_id")
	private List<EtapaProducao> listEtapas;
	
	@ManyToOne
	@JoinColumn(name = "tipoProduto_id")
	private TipoProduto tipoProduto;

	@ManyToOne
	@JoinColumn(name = "linha_id")
	private Linha linha;
	
	@OneToOne
	@JoinColumn(name = "ordemProducao_id")
	private OrdemProducao ordemProducaoOriginal;

	
}
