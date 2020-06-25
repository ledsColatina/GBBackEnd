package com.example.BackEnd.service;

import com.example.BackEnd.domain.*;
import com.example.BackEnd.dto.HorarioDTO;
import com.example.BackEnd.dto.PartidaDTO;
import com.example.BackEnd.dto.PartidaGanttDTO;
import com.example.BackEnd.dto.TarefaDTO;
import com.example.BackEnd.repository.PartidaGanttRepository;
import com.example.BackEnd.repository.TarefaRepository;
import com.example.BackEnd.service.mapper.HorarioMapper;
import com.example.BackEnd.service.mapper.PartidaGanttMapper;
import com.example.BackEnd.service.mapper.TarefaMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GanttService {

    private final HorarioService horarioService;
    private final TarefaService tarefaService;
    private final PartidaService partidaService;
    private final EtapaProducaoService etapaProducaoService;
    private final OrdemProducaoService ordemProducaoService;
    private final PartidaGanttRepository partidaGanttRepository;
    private final PartidaGanttMapper partidaGanttMapper;
    private final TurnoService turnoService;
    private final TarefaMapper tarefaMapper;
    private final MaquinaService maquinaService;
    private final HoraExtraService horaExtraService;
    private final HorarioMapper horarioMapper;

    public void preencherTarefas(Long id) throws Exception {
        List<EtapaProducao> etapas;
        OrdemProducao ordem = ordemProducaoService.buscarPorId(id);

        etapas = ordem.getListEtapas();
        etapas.forEach(etapa -> {
            List<Partida> partidas = partidaService.buscarPorEtapa(etapa.getId());
            partidas.forEach(partida -> {
                TarefaDTO tarefa = new TarefaDTO();
                tarefa.setIdOP(ordem.getId());
                tarefa.setPrioridade((long) ordem.getPrioridadeAtual());
                tarefa.setIdEtapa(etapa.getId());
                tarefa.setIdPartida(partida.getId());
                tarefaService.guardarTarefa(tarefa);
            });
        });
    }

    public void adicionarTarefa(Partida partida) throws Exception {
        TarefaDTO tarefa = new TarefaDTO();
        EtapaProducao etapa = partida.getEtapaProducao();
        OrdemProducao op = ordemProducaoService.buscarEtapa(etapa.getId());

        tarefa.setIdOP(op.getId());
        tarefa.setIdEtapa(etapa.getId());
        tarefa.setIdPartida(partida.getId());
        tarefa.setPrioridade((long) op.getPrioridadeAtual());

        tarefaService.guardarTarefa(tarefa);
    }

    public String buscarFinalEtapa(Long id) throws Exception {
        Partida partida = partidaService.buscarPorId(id);
        LocalDate data = LocalDate.now();
        Long fimAux = Long.valueOf(0);
        List<PartidaGantt> listaPartidaGantt = new ArrayList<>();
        List<Partida> listaPartida = partidaService.buscarPorEtapa(partida.getEtapaProducao().getId());
        for (Partida partida1 : listaPartida) {
            listaPartidaGantt = partidaGanttRepository.findByPartidaId(partida1.getId());
            for (PartidaGantt partidaGantt : listaPartidaGantt) {
                if(partidaGantt.getDia().isAfter(data)){
                    data = partidaGantt.getDia();
                    fimAux = partidaGantt.getFim();
                }
            }
        }
        return (data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + converterEmHoras(fimAux));
    }
    
    private String converterDuasCasas(Integer numero) {
        if(numero <= 9){
            return ("0" + numero.toString());
        }
        return numero.toString();
    }

    private String converterEmHoras(Long segundos){
        Integer hora = Math.round(segundos/3600);
        Integer minutos = Math.round((segundos%3600)/60);
        Integer seg = Math.round((segundos%3600)%60);
        return (converterDuasCasas(hora)+":"+converterDuasCasas(minutos)+":"+converterDuasCasas(seg));
    }

    public List<PartidaGanttDTO> listarTodos(){
        return partidaGanttMapper.toDto(partidaGanttRepository.findAllByOrderByMaquinaId());
    }

    public List<Tarefa> limparPartidas() throws Exception {
        List<PartidaDTO> listaPartidas = partidaService.buscarPartidasIniciadas();
        List<Tarefa> listaTarefa = new ArrayList<>();

        for (PartidaDTO partida : listaPartidas) {
            Tarefa tarefa = tarefaService.buscarPorIdPartida(partida.getIdPartida());
            if(tarefa != null && !listaTarefa.contains(tarefa)){
                List<Tarefa> tarefasPorEtapa = tarefaService.buscarPorEtapa(tarefa.getEtapa().getId());
                listaTarefa.add(tarefa);
                listaTarefa.addAll(tarefasPorEtapa);
            }
        }

        partidaGanttRepository.deleteAll();

        return listaTarefa;
    }

    public void limparHorarios(){
        List<PartidaGanttDTO> listaPartidas = listarTodos();
        List<Long> horarioId = new ArrayList<>();

        for (PartidaGanttDTO partida : listaPartidas) {
            if(!horarioId.contains(partida.getIdHorario())){
                horarioId.add(partida.getIdHorario());
            }
        }

        if(horarioId.isEmpty()){
            horarioService.deletarTodos();
        }else {
            List<Horario> listaHorario = horarioService.buscarNotInId(horarioId);
            horarioService.deletarOcioso(listaHorario);
        }
    }

    List<TarefaDTO> alocarIniciadas(List<TarefaDTO> tarefas, List<Tarefa> tarefasIniciadas) throws Exception {

        for (Tarefa tarefasIniciada : tarefasIniciadas) {
            TarefaDTO aux = null;
            for (TarefaDTO tarefa : tarefas) {
                if(tarefa.getId().equals(tarefasIniciada.getId())){
                    aux = tarefa;
                }
            }
            if(aux != null){
                tarefas.remove(aux);
            }
        }

        tarefasIniciadas.addAll(tarefaMapper.toEntity(tarefas));
        return tarefaMapper.toDto(tarefasIniciadas);
    }

    public void montarGantt() throws Exception {
        List<Tarefa> tarefas = limparPartidas();
        horarioService.deletarTodos();
        List<TarefaDTO> listaTarefas = tarefaService.listarTarefas();
        listaTarefas = alocarIniciadas(listaTarefas, tarefas);
        List<HorarioDTO> listaHorario = horarioService.listarHorarios();
        List<PartidaGanttDTO> listaPartidas;
        LocalDate data;
        data = LocalDate.now();
        String dataAux = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        data = LocalDate.parse(dataAux, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Integer index = -1;
        for (TarefaDTO tarefa : listaTarefas) {
            if(index >= 0 && !(tarefa.getIdOP().equals(listaTarefas.get(index).getIdOP()))){
                data = LocalDate.now();
            }
            Partida partida = partidaService.buscarPorId(tarefa.getIdPartida());
            data = verificarOcupacao(tarefa, data);
            index++;
        };
    }

    private boolean verificarDia(LocalDate dia, List<HorarioDTO> listHorario){
        for (HorarioDTO horarioDTO : listHorario) {
            if(horarioDTO.getDia().equals(dia)){
                return true;
            }
        }
        return false;
    }

    private Integer arredondarOcupacao(Double ocupacao){
        Integer oc = (int) Math.round(ocupacao);
        if(oc < ocupacao){
            oc++;
        }
        return oc;
    }

    private LocalDate verificarOcupacao( TarefaDTO tarefa, LocalDate data) throws Exception {
        Partida partida = partidaService.buscarPorId(tarefa.getIdPartida());
        TipoProduto tipoProduto = ordemProducaoService.buscarPorId(tarefa.getIdOP()).getTipoProduto();
        Double ocupacao = calcularOcupacao(partida.getQuantidade(), partida.getMaquina(), tipoProduto);
        List<HorarioDTO> listHorarios = horarioService.buscarPorMaquinaAPartirDeUmData(partida.getMaquina().getId(), data);
        PartidaGanttDTO ultimaPartida = new PartidaGanttDTO();
        LocalDate ultimaData;

        if(listHorarios.isEmpty()){
            ultimaData = criarHorariosIntervalo(arredondarOcupacao(ocupacao), partida.getMaquina().getId(), data);
        }
        else {
            List<HorarioDTO> listaHorarios = horarioService.buscarPorMaquinaAPartirDeHoje(partida.getMaquina().getId());
            PartidaGanttDTO partidaBase = verificarIntervalo(listaHorarios, partida, tipoProduto);

            List<PartidaGanttDTO> listaPartidaPorHorario = buscarPorHorario(listaHorarios.get(listaHorarios.size()-1).getId());
            ultimaPartida = listaPartidaPorHorario.get(listaPartidaPorHorario.size()-1);
            ultimaData = ultimaPartida.getDia();
            if(partidaBase == null){
                ultimaData = criarHorariosIntervalo(arredondarOcupacao(ocupacao), partida.getMaquina().getId(), ultimaData.plusDays(1));
            }
        }

        Tarefa tarefa1 = tarefaService.buscarPorIdPartida(partida.getId());
        PartidaGantt partidaGantt = new PartidaGantt();
        partidaGantt.setPartida(partida);
        partidaGantt.setMaquina(partida.getMaquina());
        partidaGantt.setCor(tarefa1.getOp().getCliente().getCor());
        partidaGantt.setReferencia(tarefa1.getOp().getReferencia());
        partidaGantt.setQuantidadeTotal((long) partida.getQuantidade());
        partidaGantt.setQuantidade((long) partida.getQuantidade());

        listHorarios = horarioService.buscarPorMaquina(partida.getMaquina().getId(), data);
        Integer index = 7;
        while(index > 0){
            if(!verificarDia(data, listHorarios)){
                data = data.plusDays(1);
                listHorarios = horarioService.buscarPorMaquina(partida.getMaquina().getId(), data);
            }
            else {
                index = 0;
            }
            index--;
        }

        if(ultimaPartida.getId() != null){
            partidaGantt.setHorario(horarioService.buscarPorId(ultimaPartida.getIdHorario()));
            partidaGantt.setInicio(ultimaPartida.getFim());
            partidaGantt.setDia(ultimaPartida.getDia());
        }
        else {
            partidaGantt.setHorario(horarioMapper.toEntity(horarioService.buscarPorMaquina(partida.getMaquina().getId(), data).get(0)));
            partidaGantt.setInicio(horarioMapper.toEntity(horarioService.buscarPorMaquina(partida.getMaquina().getId(), data).get(0)).getInicio());
            partidaGantt.setDia(listHorarios.get(0).getDia());
        }

        gerarPartidasGantt(partidaGantt, tarefa1);

        return ultimaData;
    }

    private LocalDate criarHorariosIntervalo(Integer totalDias, Long idMaquina, LocalDate diaInicial) throws Exception {
        if(totalDias>0){
            if(horarioService.criarHorarios(diaInicial.toString(), idMaquina)){
                return criarHorariosIntervalo((totalDias-1), idMaquina, diaInicial.plusDays(1));
            }
            else {
                return criarHorariosIntervalo(totalDias, idMaquina, diaInicial.plusDays(1));
            }
        }
        else {
            return diaInicial;
        }
    }

    private PartidaGanttDTO verificarIntervalo(List<HorarioDTO> listaHorarios, Partida partida, TipoProduto tipoProduto){
        Integer index = 1;
        List<PartidaGanttDTO> partidasPorHorario; ;
        PartidaGanttDTO primeiraPartida;
        PartidaGanttDTO ultimaPartida;
        Long tamanhoFim;
        Long tamanhoInicio;
        for (HorarioDTO horario : listaHorarios) {
            partidasPorHorario = new ArrayList<>();

            if(index < listaHorarios.size()){
                partidasPorHorario = buscarPorHorario(horario.getId());
                if(!partidasPorHorario.isEmpty()) {
                    ultimaPartida = partidasPorHorario.get(partidasPorHorario.size() - 1);
                }else {
                    return null;
                }
                    partidasPorHorario = buscarPorHorario(listaHorarios.get(index).getId());
                if(!partidasPorHorario.isEmpty()) {
                    primeiraPartida = partidasPorHorario.get((partidasPorHorario.size()) - 1);
                }else {
                    return null;
                }
                if(!ultimaPartida.getReferencia().equals(primeiraPartida.getReferencia())){
                    tamanhoFim = horario.getFim() - ultimaPartida.getFim();
                    tamanhoInicio = primeiraPartida.getInicio() - listaHorarios.get(index).getInicio();
                    Long totalPecas = converterpecas(horario.getIsHoraExtra(), tamanhoFim, horario.getIdMaquina(), tipoProduto) + converterpecas(horario.getIsHoraExtra(), tamanhoInicio, horario.getIdMaquina(), tipoProduto);
                    if(partida.getQuantidade() <= totalPecas){
                        return ultimaPartida;
                    }
                }
                index++;
            }
        }
        return null;
    }

    private List<PartidaGanttDTO> buscarPorHorario(Long id){
        return partidaGanttMapper.toDto(partidaGanttRepository.findAllByHorarioId(id));
    }

    private Long converterpecas(Boolean tipo, Long segundosLivres, Long idMaquina, TipoProduto tipoProduto){
        Long capacidade = maquinaService.buscarCapacidade(tipo, idMaquina, tipoProduto.getId());
        segundosLivres = (segundosLivres / 60) / 60;
        return segundosLivres * capacidade;
    }

    private Long verificarSegundosDisponiveis(Horario horario){
        List<PartidaGanttDTO> partidasDoHorario = buscarPorHorario(horario.getId());

        if(!partidasDoHorario.isEmpty()){
            return horario.getFim() - partidasDoHorario.get(partidasDoHorario.size()-1).getFim();
        }
        else {
            return horario.getFim() - horario.getInicio();
        }

    }

    private PartidaGantt ObjCopy(PartidaGantt partidaOrigem) {
        PartidaGantt partida = new PartidaGantt();
        partida.setMaquina(partidaOrigem.getMaquina());
        partida.setDia(partidaOrigem.getDia());
        partida.setPartida(partidaOrigem.getPartida());
        partida.setInicio(partidaOrigem.getInicio());
        partida.setHorario(partidaOrigem.getHorario());
        partida.setQuantidadeTotal(partidaOrigem.getQuantidadeTotal());
        partida.setCor(partidaOrigem.getCor());
        partida.setReferencia(partidaOrigem.getReferencia());

        return partida;
    }

    public List<PartidaGanttDTO> buscarPorRerencia(String ref){
        List<PartidaGanttDTO> listaPartidas = partidaGanttMapper.toDto(partidaGanttRepository.findAllByReferenciaContainingIgnoreCase(ref));
        return listaPartidas;
    }

    private void gerarPartidasGantt(PartidaGantt partida, Tarefa tarefa) throws Exception {

        List<HorarioDTO> horariosAux = horarioService.buscarPorMaquinaAPartirDeUmData(partida.getMaquina().getId(), partida.getDia());
        List<Horario> horariosDisponiveis = horarioMapper.toEntity(horariosAux);
        Long pecasDisponiceis;

        Integer index = 1;
        for (Horario horario : horariosDisponiveis) {
            pecasDisponiceis = converterpecas(horario.getIsHoraExtra(), verificarSegundosDisponiveis(partida.getHorario()), partida.getMaquina().getId(), tarefa.getOp().getTipoProduto());
            if(partida.getQuantidade() == 0){
                horarioService.deletarPorId(horario.getId());
            }else {
                if(pecasDisponiceis > 0){
                    PartidaGantt partidaGantt = ObjCopy(partida);
                    if(partida.getQuantidade() > pecasDisponiceis){
                        partidaGantt.setQuantidade(pecasDisponiceis);
                        partida.setQuantidade(partida.getQuantidade() - partidaGantt.getQuantidade());
                    }
                    else {
                        partidaGantt.setQuantidade(partida.getQuantidade());
                        partida.setQuantidade(Long.valueOf(0));
                    }
                    partidaGantt.setFim(calcularFim(horario, partidaGantt, tarefa.getOp().getTipoProduto()));
                    partidaGanttRepository.save(partidaGantt);
                }
                if(index < horariosDisponiveis.size()){
                    partida.setHorario(horariosDisponiveis.get(index));
                    partida.setInicio(partida.getHorario().getInicio());
                    partida.setDia(partida.getHorario().getDia());
                }
            }
            index++;
        }
    }

    private Double calcularOcupacao(Integer quantidade, Maquina maquina, TipoProduto tipoProduto) throws Exception {
        List<Turno> turnosMaquina = turnoService.buscarPorMaquina(maquina.getId());
        Integer prodDia = 0;
        for (Turno turno : turnosMaquina) {
            Long value = maquinaService.buscarCapacidade(false, maquina.getId(), tipoProduto.getId()) * turnoService.duracaoTurno(turno);
            prodDia += Integer.parseInt(value.toString());
        }
        Double ocupacao = quantidade.doubleValue() / prodDia.doubleValue();

        return ocupacao;
    }

    private Long calcularFim(Horario horario, PartidaGantt partida, TipoProduto tipoProduto) throws Exception {
        Long capacidade = maquinaService.buscarCapacidade(horario.getIsHoraExtra(), horario.getMaquina().getId(), tipoProduto.getId());
        Double prodTotalTurno = Double.valueOf(turnoService.duracaoTurno(horario.getTurno()) * capacidade);
        Double duracaoTurno = Double.valueOf(horario.getFim() - horario.getInicio());
        Double pecasSegundo = prodTotalTurno / duracaoTurno;

        return (long) (partida.getInicio() + (partida.getQuantidade() / pecasSegundo));
    }

}
