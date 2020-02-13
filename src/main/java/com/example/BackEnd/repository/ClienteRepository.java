package com.example.BackEnd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackEnd.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	List<Cliente> findByCor(String cor);

	Cliente save(Optional<Cliente> cliente);

	Cliente findTopByOrderByIdDesc();

}
