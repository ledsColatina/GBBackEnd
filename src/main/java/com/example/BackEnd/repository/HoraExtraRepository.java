package com.example.BackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackEnd.domain.HoraExtra;

public interface  HoraExtraRepository extends JpaRepository<HoraExtra, Long>{

	HoraExtra findTopByOrderByIdDesc();

}
