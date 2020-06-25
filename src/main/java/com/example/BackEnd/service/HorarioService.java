package com.example.BackEnd.service;

import com.example.BackEnd.domain.HoraExtra;
import com.example.BackEnd.domain.Horario;
import com.example.BackEnd.domain.Maquina;
import com.example.BackEnd.domain.Turno;
import com.example.BackEnd.dto.HorarioDTO;
import com.example.BackEnd.dto.TarefaDTO;
import com.example.BackEnd.repository.HorarioRepository;
import com.example.BackEnd.repository.TarefaRepository;
import com.example.BackEnd.service.exception.RegraDeNegocioException;
import com.example.BackEnd.service.mapper.HorarioMapper;
import com.example.BackEnd.service.mapper.TarefaMapper;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.tomcat.jni.Local;
import org.mockito.internal.stubbing.answers.ThrowsException;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HorarioService {

    private final HorarioRepository horarioRepository;
    private final HorarioMapper horarioMapper;
    private final TurnoService turnoService;
    private final MaquinaService maquinaService;
    private final HoraExtraService horaExtraService;

    public void guardarHorario(HorarioDTO horario) {
        horarioRepository.save(horarioMapper.toEntity(horario));
    }

    public List<HorarioDTO> buscarPorMaquina(Long id, LocalDate dia) throws Exception {
        return horarioMapper.toDto(horarioRepository.findAllByMaquinaIdAndDia(id, dia));
    }

    public void deletarOcioso(List<Horario> listaHorario){
        for (Horario horario : listaHorario) {
            horarioRepository.deleteById(horario.getId());
        }
    }

    public List<Horario> buscarNotInId(List<Long> listaId){
        return horarioRepository.findAllByIdNotIn(listaId);
    }

    public void deletarPorId(Long id){
        horarioRepository.deleteById(id);
    }

    public void deletarTodos() {
        horarioRepository.deleteAll();
    }

    public Horario buscarPorId(Long id) throws Exception {
        return horarioRepository.findById(id).orElseThrow(Exception::new);
    }

    public List<HorarioDTO> buscarPorMaquinaAPartirDeUmData(Long idMaquina, LocalDate data){
        return horarioMapper.toDto(horarioRepository.findAllByMaquinaIdAndDiaAfterOrderByDiaAscInicioAsc(idMaquina, data.plusDays(-1)));
    }

    private Integer intevaloDeHorarios() throws ParseException {
        List<Horario> listaHorarios = horarioRepository.findAllByOrderByDia();
        if(!listaHorarios.isEmpty()){
            DateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
            Date dataFim = df.parse(listaHorarios.get(listaHorarios.size()-1).getDia().toString());
            Date dataInicio = df.parse(listaHorarios.get(0).getDia().toString());
            Long tempoDecorrido = (dataFim.getTime() - dataInicio.getTime()) + 3600000;
            return Math.toIntExact(tempoDecorrido / 86400000);
        }
        return 0;
    }

    public List<String> gerarVetorDias() throws ParseException {
        Integer dias = intevaloDeHorarios();
        List<Horario> listaHorarios = horarioRepository.findAllByOrderByDia();
        List <String> listaDias = new ArrayList<>();
        if(!listaHorarios.isEmpty()){
            LocalDate dia = listaHorarios.get(0).getDia();
            do  {
                listaDias.add(dia.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                dia = dia.plusDays(1);
                dias--;
            } while(dias >= 0);
        }
        return listaDias;
    }

    public List<HorarioDTO> buscarPorMaquinaAPartirDeHoje(Long idMaquina){
        LocalDate data = LocalDate.now();
        String dataAux = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        data = LocalDate.parse(dataAux, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return horarioMapper.toDto(horarioRepository.findAllByMaquinaIdAndDiaAfterOrderByDiaAscInicioAsc(idMaquina, data.plusDays(-1)));
    }

    public boolean criarHorarios(String dia, Long idMaquina) throws Exception {
        List<Turno> turnos = turnoService.buscarPorMaquina(idMaquina);
        Maquina maquina = maquinaService.buscarPorId(idMaquina);
        LocalDate data = LocalDate.parse(dia);
        List<HorarioDTO> listaHorarios = buscarPorMaquinaAPartirDeUmData(idMaquina, data);
        HorarioDTO horario = new HorarioDTO();
        List<HoraExtra> listaHoraExtra = horaExtraService.verificarDia(maquina.getId(), data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        Boolean horarioCriado = false;

        if(!(listaHorarios.isEmpty())) {
            data = listaHorarios.get(listaHorarios.size() - 1).getDia().plusDays(1);
        }

        horario.setDia(data);
        horario.setIdMaquina(maquina.getId());
        for (Turno turno : turnos) {
            if(verificarDiaTurno(turno, dia)){
                horario.setIdTurno(turno.getId());
                horario.setInicio(converterEmSegundos(turno.getHoraInicio()));
                horario.setFim(converterEmSegundos(turno.getHoraFim()));
                horario.setIsHoraExtra(false);
                guardarHorario(horario);
                horarioCriado = true;
            }
        }
        if(!listaHoraExtra.isEmpty()){
            for (HoraExtra horaExtra : listaHoraExtra) {
                horario = new HorarioDTO();
                horario.setDia(data);
                horario.setIdMaquina(maquina.getId());
                horario.setIdTurno(horaExtra.getTurno().getId());
                horario.setInicio(converterEmSegundos(horaExtra.getHoraInicio()));
                horario.setFim(converterEmSegundos(horaExtra.getHoraFim()));
                horario.setIsHoraExtra(true);
                guardarHorario(horario);
                horarioCriado = true;
            }
        }
     
        return horarioCriado;
    }
    
    private boolean verificarDiaTurno(Turno turno, String dia) {
        String diaSemana = LocalDate.parse(dia).getDayOfWeek().toString();
        switch (diaSemana){
            case "SUNDAY":
                diaSemana = "Domingo";
                break;
            case "MONDAY":
                diaSemana = "Segunda-Feira";
                break;
            case "TUESDAY":
                diaSemana = "Terça-Feira";
                break;
            case "WEDNESDAY":
                diaSemana = "Quarta-Feira";
                break;
            case "THURSDAY":
                diaSemana = "Quinta-Feira";
                break;
            case "FRIDAY":
                diaSemana = "Sexta-Feira";
                break;
            case "SATURDAY":
                diaSemana = "Sábado";
                break;
        };
        return turno.getDiasDaSemana().contains(diaSemana);
    }

    private Long converterEmSegundos(String horaS) {
        String hora = horaS.substring(0, 2);
        String minutos = horaS.substring(3, 5);
        String segundos = horaS.substring(6, 8);
        return (Long.parseLong(hora) * 3600) + (Long.parseLong(minutos) * 60) + (Long.parseLong(segundos));
    }

    public List<HorarioDTO> listarHorarios() {
        return horarioMapper.toDto(horarioRepository.findAll());
    }

}
