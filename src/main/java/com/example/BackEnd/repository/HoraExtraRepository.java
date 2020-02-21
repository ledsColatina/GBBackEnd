package com.example.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.BackEnd.domain.HoraExtra;

public interface HoraExtraRepository extends JpaRepository<HoraExtra, Long> {

	HoraExtra findTopByOrderByIdDesc();

	@Query(value = "SELECT id, capacidade, data, momento, qtd_horas, status, turno\n" + "  FROM public.hora_extra\n"
			+ "  WHERE status = 'Pendente';", nativeQuery = true)
	List<HoraExtra> findAllPendentes();

	@Query(value = "SELECT H.id,H.capacidade,H.turno,H.data,H.qtd_horas,H.momento,H.status FROM setor as S,turno as T,hora_extra as H WHERE T.setor_id = S.id AND H.turno = T.id AND  S.id = :id", nativeQuery = true)
	List<HoraExtra> findAllHoraExtraPorSetor(@Param("id") Long id);
	
	@Query(value = "SELECT H.id,H.capacidade,H.turno,H.data,H.qtd_horas,H.momento,H.status \n" + 
			"FROM setor as S,turno as T,hora_extra as H \n" + 
			"WHERE T.setor_id = S.id AND \n" + 
			"	H.turno = T.id AND  \n" + 
			"	S.id = :id AND H.status LIKE 'Finalizado'", nativeQuery = true)
	List<HoraExtra> findAllFinalizadas(@Param("id") Long id);
	
	@Query(value = "SELECT H.id,H.capacidade,H.turno,H.data,H.qtd_horas,H.momento,H.status \n" + 
			"FROM setor as S,turno as T,hora_extra as H \n" + 
			"WHERE T.setor_id = S.id AND \n" + 
			"	H.turno = T.id AND  \n" + 
			"	S.id = :id AND H.status LIKE 'Pendente'", nativeQuery = true)
	List<HoraExtra> findAllPendentes(@Param("id") Long id);

}
