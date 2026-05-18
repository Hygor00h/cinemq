package com.cinemamq.cinemamq.infrastructure.controller;

import com.cinemamq.cinemamq.infrastructure.model.entity.FilmeEntity;
import com.cinemamq.cinemamq.infrastructure.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

	@Autowired
	private FilmeRepository filmeRepository;

	@GetMapping
	public List<FilmeEntity> findAll(){
		return filmeRepository.findAll();
	}
}
