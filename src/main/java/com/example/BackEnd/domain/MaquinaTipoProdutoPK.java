package com.example.BackEnd.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Embeddable
@Data
public class MaquinaTipoProdutoPK implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "maquina_id")
	private Maquina maquina;
	
	@ManyToOne
	@JoinColumn(name = "tipoProduto_id")
	private TipoProduto tipoProduto;
}
