package com.example.BackEnd.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PartidaGanttDTO {
    private Long id;
    private Long idHorario;
    private Long idMaquina;
    private Long idPartida;
    private String cor;
    private Long inicio;
    private LocalDate dia;
    private Long fim;
    private String referencia;
    private Long quantidade;
    private Long quantidadeTotal;
}
