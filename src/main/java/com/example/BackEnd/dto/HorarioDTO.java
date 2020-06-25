package com.example.BackEnd.dto;

import com.example.BackEnd.domain.Maquina;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class HorarioDTO {
	private Long id;
	private Long idTurno;
	private Long idMaquina;
	private LocalDate dia;
	private Long inicio;
	private Long fim;
	private Boolean isHoraExtra;
}
