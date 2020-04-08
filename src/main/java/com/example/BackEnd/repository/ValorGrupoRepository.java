package com.example.BackEnd.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BackEnd.domain.ValorGrupo;

@Transactional
public interface ValorGrupoRepository extends JpaRepository<ValorGrupo, Long> {

	ValorGrupo findTopByOrderByIdDesc();

	List<ValorGrupo> findBySubProcessoId(Long id);

	

	List<ValorGrupo> findByTipoProdutoId(Long id);

	void deleteByTipoProdutoId(Long id);



	ValorGrupo findByLinhaIdAndTipoProdutoIdAndSubProcessoId(Long id, Long id2, Long id3);




	//List<ValorGrupo> findByProcessoId(Long id);
}
