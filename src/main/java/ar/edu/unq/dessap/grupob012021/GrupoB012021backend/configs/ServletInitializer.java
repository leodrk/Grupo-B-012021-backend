package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.configs;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.GrupoB012021BackendApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GrupoB012021BackendApplication.class);
	}

}
