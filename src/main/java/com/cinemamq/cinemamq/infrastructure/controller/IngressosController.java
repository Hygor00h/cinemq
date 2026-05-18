package com.cinemamq.cinemamq.infrastructure.controller;

import com.cinemamq.cinemamq.infrastructure.config.RabbitMQConfig;
import com.cinemamq.cinemamq.infrastructure.model.dto.CompraIngressoDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingressos")
public class IngressosController {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@PostMapping("/comprar")
	public ResponseEntity<String> comprarIngresso(@RequestBody CompraIngressoDTO dto){
		rabbitTemplate.convertAndSend(RabbitMQConfig.FILA_INGRESSOS, dto);
		return ResponseEntity.accepted().body("Pedido recebido pela máquina! Aguarde o processamento do comprovante...");
	}
}
