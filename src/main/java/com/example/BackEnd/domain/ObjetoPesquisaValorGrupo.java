package com.example.BackEnd.domain;

import java.util.List;

import lombok.Data;
@Data
public class ObjetoPesquisaValorGrupo {
	private Linha linha;
	private TipoProduto tipoProduto;
	private List<SubProcesso> listSubProcesso;
}
