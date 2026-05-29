//package com.cinemamq.cinemamq.infrastructure.controller;
//
//import com.cinemamq.cinemamq.infrastructure.model.entity.SalaEntity;
//import com.cinemamq.cinemamq.infrastructure.repository.SalaRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController("/sala")
//public class SalaController {
//
//	@Autowired
//	private SalaRepository salaRepository;
//
//	@GetMapping("/{filmeId}/salas")
//	public List<SalaEntity> buscarSalaPorId(@PathVariable("salaId") UUID salaId){
//		List<SalaEntity> salaEntity= salaRepository.findByFilmeId(salaId);
//		return salaEntity;
//	}
//}
