package com.cinemamq.cinemamq.infrastructure.controller;

import com.cinemamq.cinemamq.infrastructure.mapper.AssentoMapper;
import com.cinemamq.cinemamq.infrastructure.mapper.SalaMapper;
import com.cinemamq.cinemamq.infrastructure.model.dto.SalaDto;
import com.cinemamq.cinemamq.infrastructure.model.entity.FilmeEntity;
import com.cinemamq.cinemamq.infrastructure.model.entity.SalaEntity;
import com.cinemamq.cinemamq.infrastructure.model.response.AssentoResponse;
import com.cinemamq.cinemamq.infrastructure.model.response.SalaResponse;
import com.cinemamq.cinemamq.infrastructure.repository.AssentoRepository;
import com.cinemamq.cinemamq.infrastructure.repository.FilmeRepository;
import com.cinemamq.cinemamq.infrastructure.repository.SalaRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/filmes")
public class FilmeController {

	@Autowired
	private FilmeRepository filmeRepository;

	@Autowired
	private AssentoRepository assentoRepository;

	@Autowired
	private SalaRepository salaRepository;

	@Autowired
	private SalaMapper salaMapper;

	@Autowired
	private AssentoMapper	assentoMapper;


	@GetMapping
	public List<FilmeEntity> findAll(){
		return filmeRepository.findAll();
	}

	@GetMapping("/{filmeId}/salas")
	public ResponseEntity<List<SalaResponse>> obterSalasPorFilme(@PathVariable("filmeId") UUID filmeId) {

		List<SalaEntity> salas = salaRepository.buscarSalasPorFilmeIdCustom(filmeId);
//StringUtils.isEmpty(String.valueOf(salas))
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
