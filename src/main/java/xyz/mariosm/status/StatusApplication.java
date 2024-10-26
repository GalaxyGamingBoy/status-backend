package xyz.mariosm.status;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StatusApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(StatusApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(serverTestApplication.class);
	}

}
