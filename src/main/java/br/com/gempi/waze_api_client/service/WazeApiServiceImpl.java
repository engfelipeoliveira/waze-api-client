package br.com.gempi.waze_api_client.service;

import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.replace;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.gempi.generated.Alert;
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

		// Obtem dados da api waze
		Waze waze = this.consume(wazeApiUrl).block();
		
		log.info("Inicio - Total de registros {}", this.obrasWazeJsonRepository.count());
		
		// Insere ou atualiza obras
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
					.locationX(alert.getLocation() != null && alert.getLocation().getX() != null ? replace(alert.getLocation().getX().toString(), ".", ",") : null)
					.locationY(alert.getLocation() != null && alert.getLocation().getY() != null ? replace(alert.getLocation().getY().toString(), ".", ",") : null)
					.pubMillis(alert.getPubMillis()).build();
			this.obrasWazeJsonRepository.save(obrasWazeJsonEntity);
		});
		
		// Deleta obras finalizadas
		List<String> idsWazeBd = this.obrasWazeJsonRepository.findAll().stream().map(ObrasWazeJsonEntity::getUuid).collect(toList());
		List<String> idsWazeApi = waze.getAlerts().stream().map(Alert::getUuid).collect(toList());
		List<String> idsDiff = idsWazeBd.stream().filter(element -> !idsWazeApi.contains(element)) .collect(toList());
		this.obrasWazeJsonRepository.deleteAllById(idsDiff);
		
		log.info("Fim - Total de registros {}", this.obrasWazeJsonRepository.count());
	}

	private Mono<Waze> consume(String url) {
		log.info("Consumindo API Waze");

		WebClient webClient = WebClient.create(url);

		return webClient.get().retrieve().bodyToMono(Waze.class);

	}

}
