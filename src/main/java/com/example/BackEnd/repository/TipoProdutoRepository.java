package com.example.BackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackEnd.domain.TipoProduto;

public interface TipoProdutoRepository extends JpaRepository<TipoProduto, Long>{

	TipoProduto findTopByOrderByIdDesc();
}
