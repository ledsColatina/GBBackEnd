package com.example.BackEnd.domain;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class EtapaProducao {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	//d
	private Long sequencia;
	private String inicioPrevisto;
	private String fimPrevisto;
	private Long qtdEmEspera;
	private Long qtdEmProducao;
	private Long qtdFinalizado;
	
	//@ManyToOne
	//@JoinColumn(name = "ordemProducao_id")
	//private OrdemProducao ordemProducao;
	
	@ManyToOne
	private Processo processo;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "Etapa_SubProcesso", joinColumns = { @JoinColumn(name = "subProcesso_id") }, inverseJoinColumns = {
			@JoinColumn(name = "etapaProducao_id") })
	private List<SubProcesso> listSubProcesso;
	
	@OneToMany
	private List<Partida> listPartidas;
}
