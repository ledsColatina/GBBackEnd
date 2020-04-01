package com.example.BackEnd.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackEnd.repository.OrdemProducaoRepository;

@RestController
@RequestMapping(value = "/ordemproducao")
public class OrdemProducaoResource {
	
	@Autowired
    private OrdemProducaoRepository ordemProducaoRepository;
}
