package com.example.BackEnd.service;

import com.example.BackEnd.domain.HoraExtra;
import com.example.BackEnd.domain.Horario;
import com.example.BackEnd.domain.Maquina;
import com.example.BackEnd.domain.Turno;
import com.example.BackEnd.dto.HorarioDTO;
import com.example.BackEnd.repository.HoraExtraRepository;
import com.example.BackEnd.repository.HorarioRepository;
import com.example.BackEnd.service.exception.RegraDeNegocioException;
import com.example.BackEnd.service.mapper.HorarioMapper;
import lombok.RequiredArgsConstructor;
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
public class HoraExtraService {

    private final HoraExtraRepository horaExtraRepository;

    public List<HoraExtra> verificarDia(Long idMaquina, String dia) {
        List<HoraExtra> listaHoraExtra = horaExtraRepository.findAllByMaquinaIdAndDia(idMaquina, dia);
        return listaHoraExtra;
    }

    public List<HoraExtra> buscarPorMaquinaId(Long id){
        return horaExtraRepository.findAllByMaquinaId(id);
    }

    public HoraExtra buscarPorId(Long id){
        return horaExtraRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Hora Extra não existe"));
    }

    public Long duracaoHoraExtra(HoraExtra horaExtra) {
        HoraExtra hExtra = buscarPorId(horaExtra.getId());
        Long horaInicio = Long.parseLong(hExtra.getHoraInicio().substring(0,2));
        Long horaFim = Long.parseLong(hExtra.getHoraFim().substring(0, 2));
        Long minutoInicio = Long.parseLong(hExtra.getHoraInicio().substring(3,5));
        Long minutoFim = Long.parseLong(hExtra.getHoraFim().substring(3,5));
        Long totalHora = horaFim - horaInicio;
        Long totalMinuto = (minutoFim-minutoInicio)/60;

        return totalHora+totalMinuto;
    }
}
