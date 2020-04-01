package com.example.BackEnd.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;


@Entity
@Data
public class ValorGrupo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private float valorAtual;
	
	
	
	@ManyToOne
	@JoinColumn(name = "linha_id")
	private Linha linha;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tipoProduto_id")
	private TipoProduto tipoProduto;
	
	
	@ManyToOne
	@JoinColumn(name = "SubProcesso_id")
	private SubProcesso subProcesso;
	
}
