package com.example.BackEnd.service;

import com.example.BackEnd.domain.Cliente;
import com.example.BackEnd.repository.ClienteRepository;
import com.example.BackEnd.service.exception.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Usuário inexistente"));
    }
}
