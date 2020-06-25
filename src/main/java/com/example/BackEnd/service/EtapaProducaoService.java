package com.example.BackEnd.service;

import com.example.BackEnd.domain.EtapaProducao;
import com.example.BackEnd.domain.OrdemProducao;
import com.example.BackEnd.repository.EtapaProducaoRepository;
import com.example.BackEnd.repository.OrdemProducaoRepository;
import com.example.BackEnd.repository.PartidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EtapaProducaoService {

    private final EtapaProducaoRepository etapaProducaoRepository;
    private final PartidaRepository partidaRepository;
    private final OrdemProducaoService ordemProducaoService;

    public List<EtapaProducao> listarEtapasPorOP(Long id) throws Exception {
        return ordemProducaoService.buscarPorId(id).getListEtapas();
    }

}
