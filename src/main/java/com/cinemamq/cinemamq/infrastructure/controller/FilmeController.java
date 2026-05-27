package com.cinemamq.cinemamq.infrastructure.controller;


import com.cinemamq.cinemamq.infrastructure.model.entity.AssentoEntity;
import com.cinemamq.cinemamq.infrastructure.model.entity.FilmeEntity;
import com.cinemamq.cinemamq.infrastructure.repository.AssentoRepository;
import com.cinemamq.cinemamq.infrastructure.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

	@Autowired
	private FilmeRepository filmeRepository;

	@Autowired
	private AssentoRepository assentoRepository;

	@GetMapping
	public List<FilmeEntity> findAll(){
		return filmeRepository.findAll();
	}

	@GetMapping("/{filmes}/assentos-disponiveis")
	public ResponseEntity<List<AssentoEntity>> listarTodosAssentosDisponiveis(@PathVariable("filmes") UUID filmeId){
		List<AssentoEntity> assentosLivres = assentoRepository.findByFilmeIdAndOcupadoFalse(filmeId);
		return ResponseEntity.ok(assentosLivres);
	}

}
