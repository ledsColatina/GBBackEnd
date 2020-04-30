package com.example.BackEnd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.BackEnd.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	List<Cliente> findByCor(String cor);

	Cliente save(Optional<Cliente> cliente);

	Cliente findTopByOrderByIdDesc();
	
	@Query(value = "SELECT CLI.id, CLI.cor, CLI.nome\r\n" + 
			"	FROM cliente CLI, ordem_producao as OP\r\n" + 
			"	WHERE OP.id =:id\r\n" + 
			"	and OP.cliente_id = CLI.id", nativeQuery = true)
	Cliente buscarNomeClientePorOrdemProducao(@Param("id") Long id);

}
