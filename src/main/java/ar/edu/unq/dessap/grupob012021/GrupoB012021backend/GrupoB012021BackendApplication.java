package ar.edu.unq.dessap.grupob012021.GrupoB012021backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GrupoB012021BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrupoB012021BackendApplication.class, args);
	}


}

