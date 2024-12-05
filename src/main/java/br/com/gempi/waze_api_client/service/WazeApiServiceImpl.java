package br.com.gempi.waze_api_client.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.gempi.generated.Waze;
import br.com.gempi.waze_api_client.entity.ObrasWazeJsonEntity;
import br.com.gempi.waze_api_client.repository.ObrasWazeJsonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class WazeApiServiceImpl implements WazeApiService {
	
	@Value("${waze.api.url}")
    private String wazeApiUrl;
	
	private final ObrasWazeJsonRepository obrasWazeJsonRepository;
	
	@Override
	public void getFeeds() {
		
		this.obrasWazeJsonRepository.deleteAll();
		
		Waze waze = this.consume(wazeApiUrl).block();
		waze.getAlerts().stream().forEach(alert -> {
			ObrasWazeJsonEntity obrasWazeJsonEntity = ObrasWazeJsonEntity.builder()
					.uuid(alert.getUuid())
			.country(alert.getCountry())
			.city(alert.getCity())
			.reportRating(alert.getReportRating())
			.reportByMunicipalityUser(alert.getReportByMunicipalityUser())
			.confidence(alert.getConfidence())
			.reliability(alert.getReliability())
			.type(alert.getType())
			.roadType(alert.getRoadType())
			.magvar(alert.getMagvar())
			.subType(alert.getSubtype())
			.street(alert.getStreet())
			.locationX(alert.getLocation() != null && alert.getLocation().getX() != null ? new BigDecimal(alert.getLocation().getX()) : null)
			.locationY(alert.getLocation() != null && alert.getLocation().getY() != null ? new BigDecimal(alert.getLocation().getY()) : null)
			.pubMillis(alert.getPubMillis())
					.build();
			
			this.obrasWazeJsonRepository.save(obrasWazeJsonEntity);
		});
		
		log.info("Total de registros {}", this.obrasWazeJsonRepository.count());
	}
	
	private Mono<Waze> consume(String url) {
		log.info("Consumindo API Waze");
		
		WebClient webClient = WebClient.create(url);
		
		return webClient.get()
                .retrieve()
                .bodyToMono(Waze.class);

	}

}
