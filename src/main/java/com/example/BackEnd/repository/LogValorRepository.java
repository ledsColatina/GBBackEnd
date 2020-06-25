package com.example.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackEnd.domain.LogValor;

public interface LogValorRepository extends JpaRepository<LogValor, Long> {
	List<LogValor> OrderByIdDesc();
}
