package com.cinemamq.cinemamq.infrastructure.controller;

import com.cinemamq.cinemamq.infrastructure.mapper.AssentoMapper;
import com.cinemamq.cinemamq.infrastructure.mapper.SalaMapper;
import com.cinemamq.cinemamq.infrastructure.model.entity.FilmeEntity;
import com.cinemamq.cinemamq.infrastructure.model.entity.SalaEntity;
import com.cinemamq.cinemamq.infrastructure.model.response.AssentoResponse;
import com.cinemamq.cinemamq.infrastructure.model.response.SalaResponse;
import com.cinemamq.cinemamq.infrastructure.repository.AssentoRepository;
import com.cinemamq.cinemamq.infrastructure.repository.FilmeRepository;
import com.cinemamq.cinemamq.infrastructure.repository.SalaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/filmes")
public class FilmeController {


	private final FilmeRepository filmeRepository;
	private final AssentoRepository assentoRepository;
	private final SalaRepository salaRepository;
	private final SalaMapper salaMapper;
	private final AssentoMapper assentoMapper;

	public FilmeController(FilmeRepository filmeRepository, AssentoRepository assentoRepository, SalaRepository salaRepository, SalaMapper salaMapper, AssentoMapper assentoMapper) {
		this.filmeRepository = filmeRepository;
		this.assentoRepository = assentoRepository;
		this.salaRepository = salaRepository;
		this.salaMapper = salaMapper;
		this.assentoMapper = assentoMapper;
	}

	@GetMapping
	public List<FilmeEntity> findAll() {
		return filmeRepository.findAll();
	}

	@GetMapping("/{filmeId}/salas")
	public ResponseEntity<List<SalaResponse>> obterSalasPorFilme(@PathVariable("filmeId") UUID filmeId) {

		List<SalaEntity> salas = salaRepository.buscarSalasPorFilmeIdCustom(filmeId);

		if (salas.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<SalaResponse> dtos = salaMapper.toResponse(salas);

		return ResponseEntity.ok(dtos);
	}


	@GetMapping("/salas/{salaId}/com-cadeiras")
	public ResponseEntity<List<AssentoResponse>> obterSalaEAssentos(@PathVariable UUID salaId) {//SalaEntity

		return salaRepository.buscarSalaComAssentos(salaId)
						.map(sala -> assentoMapper.toResponseList(sala.getAssentos()))
						.map(ResponseEntity::ok)
						.orElse(ResponseEntity.notFound().build());
	}

}
