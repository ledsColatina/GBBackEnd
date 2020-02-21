package com.example.BackEnd.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BackEnd.domain.Setor;

public interface SetorRepository extends JpaRepository<Setor, Long> {

	Setor save(Optional<Setor> setor);
	Setor findTopByOrderByIdDesc();
}
