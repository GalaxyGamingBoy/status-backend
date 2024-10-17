package xyz.mariosm.status;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StatusApplication {
//	public static void main(final String[] args) throws Exception {
//		String hostUrl = "http://localhost:8086";
//		char[] authToken = System.getenv("INFLUXDB_TOKEN").toCharArray();
//
//		try (InfluxDBClient client = InfluxDBClientFactory.create(hostUrl, authToken)) {
//
//		}
//	}

	public static void main(String[] args) {
		SpringApplication.run(StatusApplication.class, args);
	}

}
