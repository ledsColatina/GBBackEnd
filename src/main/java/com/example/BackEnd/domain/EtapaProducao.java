package com.example.BackEnd.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
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
	
	
	@ManyToMany()
	@JoinTable(name = "processo_etapaProducao",
				joinColumns={@JoinColumn(name="etapaProducao_id")},
				inverseJoinColumns={@JoinColumn(name="processo_id")}
	)
	private List<Processos> processos;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSequencia() {
		return sequencia;
	}
	public void setSequencia(Long sequencia) {
		this.sequencia = sequencia;
	}
	public Date getInicioPrevisto() {
		return inicioPrevisto;
	}
	public void setInicioPrevisto(Date inicioPrevisto) {
		this.inicioPrevisto = inicioPrevisto;
	}
	public Date getFimPrevisto() {
		return fimPrevisto;
	}
	public void setFimPrevisto(Date fimPrevisto) {
		this.fimPrevisto = fimPrevisto;
	}
	public Long getQtdEmEspera() {
		return qtdEmEspera;
	}
	public void setQtdEmEspera(Long qtdEmEspera) {
		this.qtdEmEspera = qtdEmEspera;
	}
	public Long getQtdEmProducao() {
		return qtdEmProducao;
	}
	public void setQtdEmProducao(Long qtdEmProducao) {
		this.qtdEmProducao = qtdEmProducao;
	}
	public Long getQtdFinalizado() {
		return qtdFinalizado;
	}
	public void setQtdFinalizado(Long qtdFinalizado) {
		this.qtdFinalizado = qtdFinalizado;
	}
	
	
}
