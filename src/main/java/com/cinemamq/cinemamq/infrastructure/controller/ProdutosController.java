package com.cinemamq.cinemamq.infrastructure.controller;


import com.cinemamq.cinemamq.infrastructure.model.entity.ProdutosEntitys;
import com.cinemamq.cinemamq.infrastructure.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/produto")
public class ProdutosController {


	private ProdutoRepository produtoRepository;

	@Autowired
	public ProdutosController(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}

	@GetMapping
	public ResponseEntity<List<ProdutosEntitys>> findProduto() {
		return ResponseEntity.ok(produtoRepository.findAll());
	}
}
