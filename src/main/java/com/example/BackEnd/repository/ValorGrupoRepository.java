package com.example.BackEnd.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.BackEnd.domain.Processos;
import com.example.BackEnd.domain.ValorGrupo;

//IDENTAR CORRETAMENTE
public interface ValorGrupoRepository extends JpaRepository<ValorGrupo, Long> {

	ValorGrupo findTopByOrderByIdDesc();
	
	
	@Query(value = "SELECT Pr.descricao,Pr.id " + 
			"FROM valor_grupo as VG " + 
			"INNER JOIN processos as Pr " + 
			"ON Pr.id = VG.processos_id " + 
			"AND VG.id = :id ", nativeQuery = true)
	String findByString(@Param("id") Long id);


	List<ValorGrupo> findByProcessoId(Long id);
	

}
