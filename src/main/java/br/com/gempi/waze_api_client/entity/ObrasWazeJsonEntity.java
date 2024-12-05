package br.com.gempi.waze_api_client.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "OBRAS_WAZE_JSON")
@Data 
@Builder 
@AllArgsConstructor
@NoArgsConstructor
public class ObrasWazeJsonEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "REPORTRATING")
	private Integer reportRating;

	@Column(name = "REPORTBYMUNICIPALITYUSER")
	private String reportByMunicipalityUser;

	@Column(name = "CONFIDENCE")
	private Integer confidence;

	@Column(name = "RELIABILITY")
	private Integer reliability;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "ROADTYPE")
	private Integer roadType;

	@Column(name = "MAGVAR")
	private Integer magvar;

	@Column(name = "SUBTYPE")
	private String subType;

	@Column(name = "STREET")
	private String street;

	@Column(name = "LOCATION_X")
	private String locationX;
	
	@Column(name = "LOCATION_Y")
	private String locationY;

	@Column(name = "PUBMILLIS")
	private Long pubMillis;

}
