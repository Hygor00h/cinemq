package com.cinemamq.cinemamq;

import com.cinemamq.cinemamq.infrastructure.model.entity.AssentoEntity;
import com.cinemamq.cinemamq.infrastructure.model.entity.FilmeEntity;
import com.cinemamq.cinemamq.infrastructure.repository.AssentoRepository;
import com.cinemamq.cinemamq.infrastructure.repository.FilmeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CinemamqApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemamqApplication.class, args);


	}
	@Bean
	CommandLineRunner iniciarDados(FilmeRepository filmeRepo, AssentoRepository assentoRepo) {
		return args -> {
			// Cadastra o filme
			FilmeEntity batman = new FilmeEntity("Batman", "Ação/Drama", "176 min", 14, 35.0);
			filmeRepo.save(batman);

			// Cria os 2 únicos assentos disponíveis que você citou
			AssentoEntity a1 = new AssentoEntity(12, batman);
			AssentoEntity a2 = new AssentoEntity(13, batman);
			assentoRepo.saveAll(List.of(a1, a2));

			System.out.println("ID do Filme para o Postman: " + batman.getId());
			System.out.println("ID do Assento 12: " + a1.getId());
			System.out.println("ID do Assento 13: " + a2.getId());
		};
	}

}
