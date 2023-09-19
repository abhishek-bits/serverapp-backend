package com.tutorial.fullstacktutorial;

import com.tutorial.entity.Server;
import com.tutorial.enums.Status;
import com.tutorial.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

@EntityScan("com.tutorial.entity")
@EnableAutoConfiguration
@EnableJpaRepositories("com.tutorial.repository")
@ComponentScan("com.tutorial.*")
@EnableWebMvc
//@CrossOrigin
@SpringBootApplication
public class FullStackTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullStackTutorialApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ServerRepository serverRepository) {
		return args -> {
			serverRepository.save(new Server(
					null,
					"192.168.1.160",
					"Ubuntu Linux",
					"16 GB",
					"Personal PC",
					"http://localhost:8080/server/image/server1.png",
					Status.SERVER_UP));
			serverRepository.save(new Server(
					null,
					"192.168.1.58",
					"Fedora Linux",
					"16 GB",
					"Dell Tower",
					"http://localhost:8080/server/image/server1.png",
					Status.SERVER_DOWN));
			serverRepository.save(new Server(
					null,
					"192.168.1.21",
					"MS 2008",
					"32 GB",
					"Web Server",
					"http://localhost:8080/server/image/server2.png",
					Status.SERVER_UP));
			serverRepository.save(new Server(
					null,
					"192.168.1.14",
					"Red Hat Enterprise Linux",
					"64 GB",
					"Mail Server",
					"http://localhost:8080/server/image/server2.png",
					Status.SERVER_DOWN));
		};
	}

	// Handle Cross-Origin errors
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList(
				"https://localhost:3000", 	// React
				"http://localhost:4200"		// Angular
		));
		corsConfiguration.setAllowedHeaders(Arrays.asList(
				"Origin",
				"Access-Control-Allow-Origin",
				"Content-Type",
				"Accept",
				"Jwt-Token",
				"Authorization",
				"Origin, Accept",
				"X-Requested-With",
				"Access-Control-Request-Method",
				"Access-Control-Request-Headers"
		));
		corsConfiguration.setExposedHeaders(Arrays.asList(
				"Origin",
				"Content-Type",
				"Accept",
				"Jwt-Token",
				"Authorization",
				"Access-Control-Allow-Origin",
				"Access-Control-Allow-Credentials",
				"Filename"
		));
		corsConfiguration.setAllowedMethods(Arrays.asList(
				"GET",
				"POST",
				"PUT",
				"PATCH",
				"DELETE",
				"OPTIONS"
		));
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
}