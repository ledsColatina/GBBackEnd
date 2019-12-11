package com.example.BackEnd.domain;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Partida {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	private Date dataInicio; 
	private Time horaInicio;
	private Date dataFim;
	private Long horaFim;
	private Long Quantidade;
	
	@ManyToOne
	@JoinColumn(name = "etapaOrigem_id")
	private EtapaProducao origem;
	
	@ManyToOne
	@JoinColumn(name = "etapaDestino_id")
	private EtapaProducao Destino;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Time getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(Time horaInicio) {
		this.horaInicio = horaInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public Long getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(Long horaFim) {
		this.horaFim = horaFim;
	}
	public Long getQuantidade() {
		return Quantidade;
	}
	public void setQuantidade(Long quantidade) {
		Quantidade = quantidade;
	}
	
	
}
