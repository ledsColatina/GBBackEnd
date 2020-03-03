package com.example.BackEnd.domain;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class HoraExtra {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	private String data;
	
	private Long capacidade;
	
	private float qtdHoras;
	
	private String status;
	
	private boolean turnoFunciona;
	//private String momento;
	
	@ManyToOne
	@JoinColumn(name = "turno")
	private Turno turno;

	
	
	public boolean isTurnoFunciona() {
		return turnoFunciona;
	}

	public void setTurnoFunciona(boolean turnoFunciona) {
		this.turnoFunciona = turnoFunciona;
	}

	/*
	public String getMomento() {
		return momento;
	}

	public void setMomento(String momento) {
		this.momento = momento;
	}
	*/
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Long getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Long capacidade) {
		this.capacidade = capacidade;
	}

	public float getQtdHoras() {
		return qtdHoras;
	}

	public void setQtdHoras(float qtdHoras) {
		this.qtdHoras = qtdHoras;
	}
	
	
}
