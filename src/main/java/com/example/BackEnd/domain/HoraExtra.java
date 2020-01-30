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
	
	private String qtdHoras;
	
	public enum status{
		FINALIZADO(1),PENDENTE(2);
		
		
		private final int valor;
		status(int valorOpcao){
	        valor = valorOpcao;
	    }
		
		
	    public int getValor(){
	        return valor;
	    }
	}
	
	@ManyToOne
	@JoinColumn(name = "turno_id")
	private Turno turno;

	
	
	
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

	public String getQtdHoras() {
		return qtdHoras;
	}

	public void setQtdHoras(String qtdHoras) {
		this.qtdHoras = qtdHoras;
	}
	
	
}
