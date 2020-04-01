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
public class LogValor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// USAR DOUBLE
	private float ValorNovo;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date data;
	private String descricao;
	private String status;

	 //@ManyToOne
	// @JoinColumn(name = "valorGrupo_id")
	 //private ValorGrupo valorGrupo;

	
}
