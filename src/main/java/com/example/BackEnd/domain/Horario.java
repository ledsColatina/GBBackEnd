package com.example.BackEnd.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Data
public class Horario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "turno_id")
	private Turno turno;

	@ManyToOne
	@JoinColumn(name = "maquina_id")
	private Maquina maquina;

	private LocalDate dia;

	private Long inicio;

	private Long fim;

	private Boolean isHoraExtra;
}
