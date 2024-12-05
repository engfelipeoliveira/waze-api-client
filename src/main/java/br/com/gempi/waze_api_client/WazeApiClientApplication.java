package br.com.gempi.waze_api_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import br.com.gempi.waze_api_client.service.WazeApiService;
import lombok.AllArgsConstructor;

@SpringBootApplication
@EnableScheduling
@AllArgsConstructor
public class WazeApiClientApplication {
	
	private final WazeApiService wazeApiService;

	public static void main(String[] args) {
		SpringApplication.run(WazeApiClientApplication.class, args);
	}
	
	@Scheduled(cron = "0 * * * * *")
	private void getFeeds() throws Exception {
		wazeApiService.getFeeds();
	}

}
