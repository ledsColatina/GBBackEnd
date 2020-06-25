package com.example.BackEnd.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MaquinaGanttDTO {
    private Long idMaquina;
    private String nomeMaquina;
    private List<PartidaGanttDTO> partidas;
}
