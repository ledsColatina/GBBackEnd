package com.example.BackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackEnd.domain.Cliente;
import com.example.BackEnd.domain.HoraExtraTipoProduto;

public interface HoraExtraTipoProdutoRepository extends JpaRepository< HoraExtraTipoProduto, Long>{

}
