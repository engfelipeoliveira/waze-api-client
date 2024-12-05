package br.com.gempi.waze_api_client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gempi.waze_api_client.entity.ObrasWazeJsonEntity;

@Repository
public interface ObrasWazeJsonRepository extends JpaRepository<ObrasWazeJsonEntity, String> {
	
}
