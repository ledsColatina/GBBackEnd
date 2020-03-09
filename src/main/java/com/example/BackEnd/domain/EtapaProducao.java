package com.example.BackEnd.domain;

import java.util.Date;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class EtapaProducao {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	private Long sequencia;
	private Date inicioPrevisto;
	private Date fimPrevisto;
	private Long qtdEmEspera;
	private Long qtdEmProducao;
	private Long qtdFinalizado;
	
	@ManyToOne
	@JoinColumn(name = "ordemProducao_id")
	private OrdemProducao ordemProducao;
	
	
	@ManyToOne
	@JoinColumn(name = "setor_id")
	private Setor setor;
	
	/*
	@ManyToMany()
	@JoinTable(name = "processo_etapaProducao",
				joinColumns={@JoinColumn(name="etapaProducao_id")},
				inverseJoinColumns={@JoinColumn(name="processo_id")}
	)*/
	//private List<Processos> processos;
	
	
	
	
}
