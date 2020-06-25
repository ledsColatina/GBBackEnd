package com.example.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.BackEnd.domain.CapacidadeProducaoExtra;
import com.example.BackEnd.domain.HoraExtra;


public interface HoraExtraRepository extends JpaRepository<HoraExtra, Long> {

	HoraExtra findTopByOrderByIdDesc();
	//---------------------------------------------------------------------------------------------------------------------
	@Query(value = "SELECT HE.id, HE.dataHE, HE.hora_fim, HE.hora_inicio, HE.momento, HE.qtd_horas, HE.status, HE.turno\r\n" +
			"	FROM hora_extra as HE\r\n" + 
			"	WHERE status = 'Pendente';", nativeQuery = true)
	List<HoraExtra> findAllPendentes();
	//----------------------------------------------------------------------------------------------------------------------
	@Query(value = "SELECT H.id,H.capacidade,H.turno,H.dataHE,H.qtd_horas,H.status,H.turno_funciona FROM setor as S,turno as T,hora_extra as H WHERE T.setor_id = S.id AND H.turno = T.id AND  S.id = :id", nativeQuery = true)
	List<HoraExtra> findAllHoraExtraPorSetor(@Param("id") Long id);
	//----------------------------------------------------------------------------------------------------------------------
	@Query(value = "SELECT H.id,H.capacidade,H.turno,H.dataHE,H.qtd_horas,H.status,H.turno_funciona \n" +
			"FROM setor as S,turno as T,hora_extra as H \n" + 
			"WHERE T.setor_id = S.id AND \n" + 
			"	H.turno = T.id AND  \n" + 
			"	S.id = :id AND H.status LIKE 'Finalizado'", nativeQuery = true)
	List<HoraExtra> findAllFinalizadas(@Param("id") Long id);
	//----------------------------------------------------------------------------------------------------------------------
	@Query(value = "SELECT H.id,H.capacidade,H.turno,H.dataHE,H.qtd_horas,H.status,H.turno_funciona\n" +
			"FROM setor as S,turno as T,hora_extra as H \n" + 
			"WHERE T.setor_id = S.id AND \n" + 
			"	H.turno = T.id AND  \n" + 
			"	S.id = :id AND H.status LIKE 'Pendente'", nativeQuery = true)
	List<HoraExtra> findAllPendentes(@Param("id") Long id);
	//----------------------------------------------------------------------------------------------------------------------
	@Query(value = "SELECT HE.id, HE.dataHE, HE.hora_fim, HE.hora_inicio, HE.momento, HE.qtd_horas, HE.status, HE.turno\r\n" +
			"	FROM hora_extra as HE\r\n" + 
			"	WHERE HE.id = :id", nativeQuery = true)
	HoraExtra findByPegarHoraExtra(@Param("id") Long id);
	
	@Query(value = "SELECT HE.id, HE.dataHE, HE.hora_fim, HE.hora_inicio, HE.momento, HE.qtd_horas, HE.status, HE.turno\r\n" +
			"	FROM hora_extra as HE\r\n" + 
			"	WHERE status = 'Finalizada';", nativeQuery = true)
	List<HoraExtra> findAllFinalizadas();
	
	@Query(value = "SELECT   CP.capacidade_hora\r\n" + 
			"	FROM capacidade_producao as CP,hora_extra as HE\r\n" + 
			"	WHERE CP.hora_extra_id = HE.id\r\n" + 
			"	and HE.id = :id", nativeQuery = true)
	List<CapacidadeProducaoExtra> findAllCapacidadesPorHoraExtra(@Param("id") Long id);

	@Query(value = "SELECT he.id, he.dataHE, he.hora_fim, he.hora_inicio, he.momento, he.qtd_horas, he.status, he.turno FROM hora_extra_maquina hm, hora_extra he " +
 			"WHERE he.id = hm.hora_extra_id and hm.maquina_id = :idMaquina and he.dataHE = :dia", nativeQuery = true)
	List<HoraExtra> findAllByMaquinaIdAndDia(@Param("idMaquina") Long idMaquina, @Param("dia") String dia);

	@Query(value = "SELECT he.id, he.dataHE, he.hora_fim, he.hora_inicio, he.momento, he.qtd_horas, he.status, he.turno FROM hora_extra_maquina hm, hora_extra he " +
			"WHERE he.id = hm.hora_extra_id and hm.maquina_id = :id", nativeQuery = true)
	List<HoraExtra> findAllByMaquinaId(@Param("id") Long id);

	List<HoraExtra> findByStatusLike(String string);
}
