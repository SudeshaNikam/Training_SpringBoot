/**
 * 
 */
package com.ticket.dto;

import java.sql.Time;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author SUDESHA
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TrainResponseDto {

	private Long trainId;

	@NotBlank(message = "trainNumber is mandatory")
	private String trainNumber;

	private String trainName;

	@Pattern(regexp = "[a-zA-z]+", message = "source should be valid")
	@NotBlank(message = "source is mandatory")
	private String source;

	@Pattern(regexp = "[a-zA-z]+", message = "destination should be valid")
	@NotBlank(message = "destination is mandatory")
	private String destination;

	private Time duration;

	@Min(value = 10, message = "must be equal or greater than 10")
	private int fare;

	@Range(min = 50, max = 500)
	private int availableSeats;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Future
	private String date;

}
