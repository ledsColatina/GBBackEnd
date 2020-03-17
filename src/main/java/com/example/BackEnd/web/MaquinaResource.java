package com.example.BackEnd.web;

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
import com.example.BackEnd.domain.Maquina;
import com.example.BackEnd.domain.MaquinaTipoProduto;
import com.example.BackEnd.domain.MaquinaTipoProdutoPK;
import com.example.BackEnd.domain.TipoProduto;
import com.example.BackEnd.domain.Turno;
import com.example.BackEnd.repository.MaquinaRepository;
import com.example.BackEnd.repository.MaquinaTipoProdutoRepository;
import com.example.BackEnd.repository.TipoProdutoRepository;
import com.example.BackEnd.repository.TurnoRepository;
import com.example.BackEnd.service.SetorService;

@RestController
@RequestMapping(value = "/maquina")
public class MaquinaResource {

	@Autowired
	private MaquinaRepository maquinaRepository;

	@Autowired
	private TurnoRepository turnoRepository;
	
	@Autowired
	private TipoProdutoRepository tipoProdutoRepository;
	
	@Autowired
	private SetorService setorService;
	
	@Autowired
	private MaquinaTipoProdutoRepository maquinaTipoProdutoRepository;
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	@GetMapping()
	protected ResponseEntity<List<Maquina>> listar() {
		List<Maquina> setor = maquinaRepository.findAll();
		return !setor.isEmpty() ? ResponseEntity.ok(setor) : ResponseEntity.noContent().build();
	}

	//-----------------------------------------------------------------------------------------------------------------------	
	
	@GetMapping("/{id}/turnos")
	protected ResponseEntity<?> listarTurnos(@PathVariable Long id) {
		Optional<Maquina> setor = maquinaRepository.findById(id);
		return setor.isPresent() ? ResponseEntity.ok(setor.get().getListTurno()) : ResponseEntity.noContent().build();
	}

	//-----------------------------------------------------------------------------------------------------------------------	
	
	@GetMapping("/lastID")
	public ResponseEntity<?> pegarUltimoIDSetor() {
		Maquina setor = maquinaRepository.findTopByOrderByIdDesc();
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
		System.out.println(maquinaRepository.existsById(id));
		return maquinaRepository.existsById(id);
	}
	
	
	//---------------------------------------------------
	
		@GetMapping("/capacidade/{id}")
		public ResponseEntity<List<MaquinaTipoProduto>> pegarCapacidadeDeCadaMaquina(@PathVariable Long id) {
			List<MaquinaTipoProduto> mtp = maquinaTipoProdutoRepository.findAllCapacidadeDeMaquinasPorTipoProduto(id);
			return ResponseEntity.status(HttpStatus.OK).body(mtp);
		}
		
		
		
	//-----------------------------------------------------------------------------------------------------------------------
	
	@PostMapping("/turno")
	public ResponseEntity<Turno> criarTurno(@Valid @RequestBody Turno turno) {
		Turno turnoSalvo = turnoRepository.save(turno);
		return ResponseEntity.status(HttpStatus.OK).body(turnoSalvo);
	}

	//-----------------------------------------------------------------------------------------------------------------------
	
		@PostMapping("/maquinatipoproduto")
		public ResponseEntity<?> criarMaquinaTipoProduto(@Valid @RequestBody Maquina maquina,TipoProduto tipoProduto,MaquinaTipoProduto maquinaTipoProduto) {
			MaquinaTipoProdutoPK chavaComposta = new MaquinaTipoProdutoPK();
			chavaComposta.setMaquina(maquina);
			chavaComposta.setTipoProduto(tipoProduto);
			
			MaquinaTipoProduto tipoProd = new MaquinaTipoProduto();
			tipoProd.setCapacidadeHora(maquinaTipoProduto.getCapacidadeHora());
			tipoProd.setChaveComposta(chavaComposta);
			MaquinaTipoProduto maquinaTP = maquinaTipoProdutoRepository.save(tipoProd);
			
			return ResponseEntity.status(HttpStatus.OK).body(maquinaTP);
		}

		
	//----------------------------------------------------------------------------------------------------------------------------

	@PostMapping
	public ResponseEntity<?> criarSetor(@Valid @RequestBody Maquina maquina, HttpServletResponse responseEntity) {
		Maquina maquinaSalvo = maquinaRepository.save(maquina);
		List<TipoProduto> listTipoProd = tipoProdutoRepository.findAll();
		MaquinaTipoProdutoPK chaveComposta = new MaquinaTipoProdutoPK();
		MaquinaTipoProduto MaquinaTipoProd = new MaquinaTipoProduto();
		
		System.out.println(listTipoProd.size());
		for(int i=0;i<listTipoProd.size();i++) {
			chaveComposta.setMaquina(maquinaSalvo);
			System.out.println(chaveComposta.getMaquina().getNome());
			chaveComposta.setTipoProduto(listTipoProd.get(i));
			
			MaquinaTipoProd.setCapacidadeHora(0);
			MaquinaTipoProd.setChaveComposta(chaveComposta);
			maquinaTipoProdutoRepository.save(MaquinaTipoProd);
		}
		
		return ResponseEntity.ok(maquinaSalvo);
	}

	//-----------------------------------------------------------------------------------------------------------------------	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	protected ResponseEntity<?> deleteSetor(@PathVariable Long id) {
		List<Maquina> maquinaEncontrado = setorService.deleteSetor(id);
		return !maquinaEncontrado.isEmpty() ? ResponseEntity.ok(maquinaEncontrado) : ResponseEntity.noContent().build();
	}
	
	
	//-----------------------------------------------------------------------------------------------------------------------	
	
		@DeleteMapping("/turno/{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		protected void deleteTurno(@PathVariable Long id) {
			turnoRepository.deleteById(id);
		}
		
	//-----------------------------------------------------------------------------------------------------------------------	
	
	@PutMapping("/{id}")
	protected ResponseEntity<Maquina> atualizaSetor(@PathVariable("id") Long id, @RequestBody Maquina maquina,HttpServletResponse responseEntity) {
		return maquinaRepository.findById(id).map(record -> {
			record.setNome(maquina.getNome());
			record.setMaxOcupacao(maquina.getMaxOcupacao());
			record.setRole(maquina.getRole());
			record.setListTurno(maquina.getListTurno());
			Maquina updated = maquinaRepository.save(record);
			return ResponseEntity.ok().body(updated);

		}).orElse(ResponseEntity.notFound().build());
	}

	
	//----------------------------------------------------------------------------------------------------------------------	
	/*
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
	*/
}
