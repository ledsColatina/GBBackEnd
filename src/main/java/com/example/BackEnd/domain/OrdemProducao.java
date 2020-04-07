package com.example.BackEnd.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
	private String emissao;
	private int prioridadeAtual;
	private int referencia;
	private boolean reprocesso;
	private double valorTotal;
	private int prioridadeInicial;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@OneToMany
	@JoinColumn(name = "etapaProducao_id")
	private List<EtapaProducao> listEtapaProducao;
	
	@ManyToOne
	@JoinColumn(name = "tipoProduto_id")
	private TipoProduto tipoProduto;

	@ManyToOne
	@JoinColumn(name = "linha_id")
	private Linha linha;
	
	@OneToOne
	@JoinColumn(name = "ordemProducao_id")
	private EtapaProducao ordemProcucaoOriginal;

	
}
