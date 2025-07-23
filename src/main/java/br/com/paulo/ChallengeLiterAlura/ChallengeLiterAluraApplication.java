package br.com.paulo.ChallengeLiterAlura;

import br.com.paulo.ChallengeLiterAlura.Principal.Principal;
import br.com.paulo.ChallengeLiterAlura.Repository.AutorRepository;
import br.com.paulo.ChallengeLiterAlura.Repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.Scanner;
@Component
@SpringBootApplication
public class ChallengeLiterAluraApplication implements CommandLineRunner {
	@Autowired
	private LivroRepository repository;
	@Autowired
	private AutorRepository autorRepository;


	public static void main(String[] args)  {
		SpringApplication.run(ChallengeLiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		 Scanner sc = new Scanner(System.in);
		Principal principal = new Principal(repository,autorRepository);


		principal.menu();


	}
}
