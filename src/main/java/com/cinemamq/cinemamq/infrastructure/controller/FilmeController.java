package com.cinemamq.cinemamq.infrastructure.controller;


import com.cinemamq.cinemamq.infrastructure.model.entity.AssentoEntity;
import com.cinemamq.cinemamq.infrastructure.model.entity.FilmeEntity;
import com.cinemamq.cinemamq.infrastructure.model.entity.SalaEntity;
import com.cinemamq.cinemamq.infrastructure.repository.AssentoRepository;
import com.cinemamq.cinemamq.infrastructure.repository.FilmeRepository;
import com.cinemamq.cinemamq.infrastructure.repository.SalaRepository;
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

	@Autowired
	private SalaRepository salaRepository;

	@GetMapping("/{filmeId}/salas")
	public ResponseEntity<List<SalaEntity>> obterSalasPorFilme(@PathVariable("filmeId") UUID filmeId) {

		// Agora sim: Passando o ID do filme para a query que busca por filme!
		List<SalaEntity> salas = salaRepository.buscarSalasPorFilmeIdCustom(filmeId);

		if (salas.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(salas);
	}

	@GetMapping
	public List<FilmeEntity> findAll(){
		return filmeRepository.findAll();
	}

	@GetMapping("/salas/{salaId}/com-cadeiras")
	public ResponseEntity<SalaEntity> obterSalaEAssentos(@PathVariable UUID salaId) {

		// Busca a sala usando a query customizada com JOIN FETCH
		return salaRepository.buscarSalaComAssentos(salaId)
						.map(sala -> ResponseEntity.ok(sala)) // Se achar a sala, devolve 200 OK com ela
						.orElse(ResponseEntity.notFound().build()); // Se não achar, devolve 404
	}

}
