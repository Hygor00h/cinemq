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

			FilmeEntity batman = new FilmeEntity("Batman", "Ação/Drama", "176 min", 14, 35.0);
			filmeRepo.save(batman);

			// 2. Loop para criar as 10 cadeiras (de 1 a 10) para esse filme
			for (int i = 1; i <= 10; i++) {
				AssentoEntity assento = new AssentoEntity(i, batman);
				assentoRepo.save(assento);
			}

			System.out.println("\n=== SISTEMA PRONTO PARA OS TESTES ===");
			System.out.println("ID do Filme para copiar no Postman: " + batman.getId());
			System.out.println("Cadeiras criadas no banco do ID 1 ao 10 com status DISPONÍVEL.");
			System.out.println("=====================================\n");

			// Cadastra o filme
//			FilmeEntity batman = new FilmeEntity("Batman", "Ação/Drama", "176 min", 14, 35.0);
//			filmeRepo.save(batman);
//
//			// Cria os 2 únicos assentos disponíveis que você citou
//			AssentoEntity a1 = new AssentoEntity(12, batman);
//			AssentoEntity a2 = new AssentoEntity(13, batman);
//			assentoRepo.saveAll(List.of(a1, a2));

//			System.out.println("ID do Filme para o Postman: " + batman.getId());
//			System.out.println("ID do Assento 12: " + a1.getId());
//			System.out.println("ID do Assento 13: " + a2.getId());
		};
	}

}
