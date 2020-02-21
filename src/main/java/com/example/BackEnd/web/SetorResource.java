package com.example.BackEnd.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.BackEnd.domain.Setor;
import com.example.BackEnd.domain.Turno;
import com.example.BackEnd.repository.SetorRepository;
import com.example.BackEnd.repository.TurnoRepository;

@RestController
@RequestMapping(value = "/setor")
public class SetorResource {

	@Autowired
	private SetorRepository setorRepository;

	@Autowired
	private TurnoRepository turnoRepository;
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	@GetMapping()
	protected ResponseEntity<List<Setor>> listar() {
		List<Setor> setor = setorRepository.findAll();
		return !setor.isEmpty() ? ResponseEntity.ok(setor) : ResponseEntity.noContent().build();
	}

	//-----------------------------------------------------------------------------------------------------------------------	
	
	@GetMapping("/{id}/turnos")
	protected ResponseEntity<?> listarTurnos(@PathVariable Long id) {
		Optional<Setor> setor = setorRepository.findById(id);
		return setor.isPresent() ? ResponseEntity.ok(setor.get().getListTurno()) : ResponseEntity.noContent().build();
	}

	//-----------------------------------------------------------------------------------------------------------------------	
	
	@GetMapping("/lastID")
	public ResponseEntity<?> pegarUltimoIDSetor() {
		Setor setor = setorRepository.findTopByOrderByIdDesc();
		if (setor != null)
			return ResponseEntity.ok(setor.getId() + 1);
		else
			return ResponseEntity.ok(1);
	}

	//-----------------------------------------------------------------------------------------------------------------------	
	
	@GetMapping("/{id}/turnos/lastID")
	public ResponseEntity<?> pegarUltimoIDTurno() {
		Turno turno = turnoRepository.findTopByOrderByIdDesc();
		if (turno != null)
			return ResponseEntity.ok(turno.getId() + 1);
		else
			return ResponseEntity.ok(1);
	}

	//---------------------------------------------------
	
	@GetMapping("/{id}")
	public boolean searchSetorById(@PathVariable Long id) {
		System.out.println(setorRepository.existsById(id));
		return setorRepository.existsById(id);
	}

	//-----------------------------------------------------------------------------------------------------------------------
	
	@PostMapping("turno")
	public void criarTurno(@Valid @RequestBody Turno turno) {
		turnoRepository.save(turno);
	}

	//----------------------------------------------------------------------------------------------------------------------------

	@PostMapping
	public ResponseEntity<Setor> criarSetor(@Valid @RequestBody Setor setor, HttpServletResponse responseEntity) {
		Setor setorSalvo = setorRepository.save(setor);
		return ResponseEntity.ok(setorSalvo);
	}

	//-----------------------------------------------------------------------------------------------------------------------	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	protected ResponseEntity<?> deleteSetor(@PathVariable Long id) {
		List<Setor> listaSetor = setorRepository.findAll();
		List<Setor> setorEncontrado = new ArrayList<Setor>();
		int cont = 0;

		for (int i = 0; i < listaSetor.size(); i++) {
			if (listaSetor.get(i).getId() == id) {
				setorEncontrado.add(listaSetor.get(i));
				setorRepository.deleteById(id);
				cont = cont + 1;
			}
		}
		return !setorEncontrado.isEmpty() ? ResponseEntity.ok(setorEncontrado) : ResponseEntity.noContent().build();
	}

	//-----------------------------------------------------------------------------------------------------------------------	
	
	@PutMapping("/{id}")
	protected ResponseEntity<Setor> atualizaSetor(@PathVariable("id") Long id, @RequestBody Setor setor,HttpServletResponse responseEntity) {
		return setorRepository.findById(id).map(record -> {
			record.setNome(setor.getNome());
			record.setMaxOcupacao(setor.getMaxOcupacao());
			record.setRole(setor.getRole());
			record.setListTurno(setor.getListTurno());
			Setor updated = setorRepository.save(record);
			return ResponseEntity.ok().body(updated);

		}).orElse(ResponseEntity.notFound().build());
	}

	//----------------------------------------------------------------------------------------------------------------------	
	
	public int verficarRegrasDeNegocio(Setor setor) {
		int totalDeHorasDoSetor = 0;
		int minutosInicio = 0;
		int minutosFim = 0;
		int cont = 0;
		int contadorDeVerificacaoEntreOsTurnos = 1;
		ArrayList<Integer> vetorHorasInicio = new ArrayList<Integer>();
		ArrayList<Integer> vetorHorasFinal = new ArrayList<Integer>();
		int totalDeTurnos = 0;
		int totalDeHorasDoTurno = 0;
		for (int b = 0; b < setor.getListTurno().size(); b++) {
			totalDeTurnos = totalDeTurnos + 1;
		}

		if (totalDeTurnos == 1) {
			//turnoRepository.save(setor);
			return 0;
		}

		// FAVOR USAR O JAVA 8, trocar para foreach
		for (int c = 0; c < setor.getListTurno().size(); c++) {
			totalDeHorasDoSetor = totalDeHorasDoSetor + setor.getListTurno().get(c).getTotalHoras();
		}
		

		for (int i = 0; i < setor.getListTurno().size(); i++) {
			totalDeHorasDoTurno = totalDeHorasDoTurno + setor.getListTurno().get(i).getTotalHoras();
			String str = setor.getListTurno().get(i).getHoraInicio();
			String[] arr = str.split(":");

			if (arr.length == 2) {
				minutosInicio = Integer.parseInt(arr[0]) * 60 + Integer.parseInt(arr[1]);
			}

			minutosFim = (totalDeHorasDoTurno * 60) + minutosInicio;

			if (minutosFim > 24 * 60) {
				minutosFim = minutosFim - 24 * 60;
			}

			
			if (cont > 0) {
				for (int j = 0; j < vetorHorasInicio.size(); j++) {

					if ((minutosFim >= vetorHorasInicio.get(j) && minutosFim >= vetorHorasFinal.get(j))
							|| (minutosInicio / 60 >= vetorHorasInicio.get(j) / 60
									&& minutosInicio / 60 >= vetorHorasFinal.get(j) / 60)) {
						System.out.println("IF");
						System.out.println("(" + minutosFim / 60 + " >= " + vetorHorasInicio.get(j) / 60 + " && "
								+ minutosFim / 60 + " >= " + vetorHorasFinal.get(j) / 60 + ")" + " || " + "("
								+ minutosInicio / 60 + " >= " + vetorHorasInicio.get(j) / 60 + " && "
								+ minutosInicio / 60 + " >= " + vetorHorasFinal.get(j) / 60 + ")");

						if (minutosInicio >= vetorHorasFinal.get(j)) {
							//turnoRepository.save(setor);
							return 0;
						}
						return 1;
					} else {
						System.out.println("ELSE");
						System.out.println("(" + minutosFim / 60 + " <= " + vetorHorasInicio.get(j) / 60 + " && "
								+ minutosFim / 60 + " <= " + vetorHorasFinal.get(j) / 60 + ")" + " && " + "("
								+ minutosInicio / 60 + " <= " + vetorHorasInicio.get(j) / 60 + " && "
								+ minutosInicio / 60 + " <= " + vetorHorasFinal.get(j) / 60 + ")");

						System.out.println(minutosInicio / 60 + " >= " + vetorHorasFinal.get(j) / 60 + " && "
								+ minutosInicio / 60 + " >= " + vetorHorasInicio.get(j) / 60);
						System.out.println(minutosInicio / 60 + " <= " + vetorHorasFinal.get(j) / 60 + " && "
								+ minutosInicio / 60 + " <= " + vetorHorasInicio.get(j) / 60);

						if ((minutosInicio >= vetorHorasFinal.get(j) && minutosInicio >= vetorHorasInicio.get(j))
								|| (minutosInicio <= vetorHorasFinal.get(j)
										&& minutosInicio <= vetorHorasInicio.get(j))) {
							System.out.println(minutosFim / 60 + " >= " + vetorHorasFinal.get(j) / 60 + " && "
									+ minutosFim / 60 + " >= " + vetorHorasInicio.get(j) / 60);
							System.out.println(minutosFim / 60 + " <= " + vetorHorasFinal.get(j) / 60 + " && "
									+ minutosFim / 60 + " <= " + vetorHorasInicio.get(j) / 60);

							if ((minutosFim >= vetorHorasFinal.get(j) && minutosFim >= vetorHorasInicio.get(j))
									|| (minutosFim <= vetorHorasFinal.get(j)
											&& minutosFim <= vetorHorasInicio.get(j))) {
								contadorDeVerificacaoEntreOsTurnos = contadorDeVerificacaoEntreOsTurnos + 1;
								System.out.println("cont :" + contadorDeVerificacaoEntreOsTurnos + " // "
										+ "totalDeTurnos" + totalDeTurnos);
								if (contadorDeVerificacaoEntreOsTurnos + 1 == totalDeTurnos) {
									if (totalDeHorasDoSetor < 24) {
										//turnoRepository.save(setor);
										return 0;
									} else {
										return 1;
									}
								}
							}
						}
					}
				}
			}
			contadorDeVerificacaoEntreOsTurnos = 0;
			vetorHorasInicio.add(minutosInicio);
			vetorHorasFinal.add((totalDeHorasDoTurno * 60) + minutosInicio);
			cont = cont + 1;
			totalDeHorasDoTurno = 0;

		}
		if (cont == 1) {
			//turnoRepository.save(setor);
			return 0;
		}

		return 1;
	}
}
