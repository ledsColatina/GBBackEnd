package com.example.BackEnd.service;

import com.example.BackEnd.domain.EtapaProducao;
import com.example.BackEnd.domain.Maquina;
import com.example.BackEnd.domain.OrdemProducao;
import com.example.BackEnd.domain.Partida;
import com.example.BackEnd.repository.OrdemProducaoRepository;
import com.example.BackEnd.service.exception.RegraDeNegocioException;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrdemProducaoService {

    @Autowired
    private OrdemProducaoRepository ordemProducaoRepository;

    @Autowired
    private PartidaService partidaService;

    @Autowired
    private MaquinaService maquinaService;

    public OrdemProducao buscarPorId(Long id) throws Exception {
        return ordemProducaoRepository.findById(id).orElseThrow(Exception::new);
    }

    public OrdemProducao buscarPorReferencia(String ref) {
        return ordemProducaoRepository.findByReferencia(ref).orElseThrow( () -> new RegraDeNegocioException("OP inexistente"));
    }

    public List<OrdemProducao> buscarOrdenadoPorPrioridade() {
        return ordemProducaoRepository.findAllByOrderByPrioridadeAtualDesc();
    }

    public OrdemProducao buscarEtapa(Long id) throws Exception {
        return ordemProducaoRepository.findByEtapaProducaoId(id).orElseThrow(Exception::new);
    }

}
