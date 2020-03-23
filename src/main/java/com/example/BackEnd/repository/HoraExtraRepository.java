package com.example.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.BackEnd.domain.HoraExtra;
import com.example.BackEnd.domain.HoraExtraTipoProduto;

public interface HoraExtraRepository extends JpaRepository<HoraExtra, Long> {

	HoraExtra findTopByOrderByIdDesc();
	//---------------------------------------------------------------------------------------------------------------------
	@Query(value = "SELECT id, capacidade, data, momento, qtd_horas, status, turno\n" + "  FROM public.hora_extra\n"
			+ "  WHERE status = 'Pendente';", nativeQuery = true)
	List<HoraExtra> findAllPendentes();
	//----------------------------------------------------------------------------------------------------------------------
	@Query(value = "SELECT H.id,H.capacidade,H.turno,H.data,H.qtd_horas,H.status,H.turno_funciona FROM setor as S,turno as T,hora_extra as H WHERE T.setor_id = S.id AND H.turno = T.id AND  S.id = :id", nativeQuery = true)
	List<HoraExtra> findAllHoraExtraPorSetor(@Param("id") Long id);
	//----------------------------------------------------------------------------------------------------------------------
	@Query(value = "SELECT H.id,H.capacidade,H.turno,H.data,H.qtd_horas,H.status,H.turno_funciona \n" + 
			"FROM setor as S,turno as T,hora_extra as H \n" + 
			"WHERE T.setor_id = S.id AND \n" + 
			"	H.turno = T.id AND  \n" + 
			"	S.id = :id AND H.status LIKE 'Finalizado'", nativeQuery = true)
	List<HoraExtra> findAllFinalizadas(@Param("id") Long id);
	//----------------------------------------------------------------------------------------------------------------------
	@Query(value = "SELECT H.id,H.capacidade,H.turno,H.data,H.qtd_horas,H.status,H.turno_funciona\n" + 
			"FROM setor as S,turno as T,hora_extra as H \n" + 
			"WHERE T.setor_id = S.id AND \n" + 
			"	H.turno = T.id AND  \n" + 
			"	S.id = :id AND H.status LIKE 'Pendente'", nativeQuery = true)
	List<HoraExtra> findAllPendentes(@Param("id") Long id);
	//----------------------------------------------------------------------------------------------------------------------
	@Query(value = "SELECT HTP.id, HTP.capacidade, HTP.hora_extra_id, HTP.tipo_produto_id\r\n" + 
			"	FROM public.hora_extra_tipo_produto as HTP, hora_extra as HE\r\n" + 
			"	WHERE HE.id = HTP.hora_extra_id\r\n" + 
			"	and HE.id = :id", nativeQuery = true)
	List<HoraExtraTipoProduto> PegarHoraExtraTipoProduto(@Param("id")Long id);
}
