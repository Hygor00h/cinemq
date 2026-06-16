package com.cinemamq.cinemamq.infrastructure.controller;

import com.cinemamq.cinemamq.infrastructure.mapper.SalaMapper;
import com.cinemamq.cinemamq.infrastructure.model.dto.SalaDto;
import com.cinemamq.cinemamq.infrastructure.model.entity.FilmeEntity;
import com.cinemamq.cinemamq.infrastructure.model.entity.SalaEntity;
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

	@GetMapping("/{filmeId}/salas")
	public ResponseEntity<List<SalaDto>> obterSalasPorFilme(@PathVariable("filmeId") UUID filmeId) {

		List<SalaEntity> salas = salaRepository.buscarSalasPorFilmeIdCustom(filmeId);

		if (StringUtils.isEmpty(String.valueOf(salas))) {
			return ResponseEntity.noContent().build();
		}

		List<SalaDto> dtos = salaMapper.toDtoList(salas);

		return ResponseEntity.ok(dtos);
	}

	@GetMapping
	public List<FilmeEntity> findAll(){
		return filmeRepository.findAll();
	}

	@GetMapping("/salas/{salaId}/com-cadeiras")
	public ResponseEntity<SalaEntity> obterSalaEAssentos(@PathVariable UUID salaId) {

		return salaRepository.buscarSalaComAssentos(salaId)
						.map(sala -> ResponseEntity.ok(sala))
						.orElse(ResponseEntity.notFound().build());
	}

}
