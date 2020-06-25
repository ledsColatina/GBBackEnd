package com.example.BackEnd.web;

import com.example.BackEnd.domain.Cliente;
import com.example.BackEnd.domain.OrdemProducao;
import com.example.BackEnd.domain.StringData;
import com.example.BackEnd.dto.PartidaGanttDTO;
import com.example.BackEnd.dto.TarefaDTO;
import com.example.BackEnd.repository.ClienteRepository;
import com.example.BackEnd.service.GanttService;
import com.example.BackEnd.service.HorarioService;
import com.example.BackEnd.service.TurnoService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/gantt")
@RequiredArgsConstructor
public class GanttResource {

	private final GanttService ganttService;
	private final HorarioService horarioService;

	@GetMapping()
	public ResponseEntity<List<PartidaGanttDTO>> gerarGantt() throws Exception {
		ganttService.montarGantt();
		List<PartidaGanttDTO> listaPartidas = ganttService.listarTodos();
		return ResponseEntity.ok(listaPartidas);
	}

	@GetMapping(value = "/finaletapa/{id}", produces = "application/json")
	public ResponseEntity<StringData> buscarFinalEtapa(@PathVariable("id") Long id) throws Exception {
		String dataFinal = ganttService.buscarFinalEtapa(id);
		StringData response = new StringData();
		response.setData(dataFinal);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/dias")
	public ResponseEntity<List<String>> getDias() throws ParseException {
		List<String> dias = horarioService.gerarVetorDias();
		return ResponseEntity.ok(dias);
	}

	@GetMapping("/filtro/{referencia}")
	public ResponseEntity<List<PartidaGanttDTO>> buscarReferencia(@PathVariable("referencia") String ref){
		List<PartidaGanttDTO> listaPartida = ganttService.buscarPorRerencia(ref);
		return ResponseEntity.ok(listaPartida);
	}

	@PostMapping("/{id}")
	public ResponseEntity<Void> cadastrarTarefas(@PathVariable Long id) throws Exception {
		ganttService.preencherTarefas(id);
		return ResponseEntity.ok(null);
	}

//	@PostMapping("/{idTurno}/{idMaquina}")
//	public ResponseEntity<Void> cadastrarHorario(@PathVariable Long idTurno, @PathVariable Long idMaquina) throws Exception {
//		horarioService.criarHorarios(idTurno, idMaquina);
//		return ResponseEntity.ok(null);
//	}

}


