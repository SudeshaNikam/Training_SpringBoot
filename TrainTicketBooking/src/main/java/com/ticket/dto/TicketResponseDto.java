/**
 * 
 */
package com.ticket.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
public class TicketResponseDto {

	@NotBlank(message = "trainNumber is mandatory")
	private String ticketNumber;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Future
	private String dateOfJourney;

	@NotBlank(message = "trainNumber is mandatory")
	private String trainNumber;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String dateOfBooking;

	@Min(value = 10, message = "must be equal or greater than 10")
	@NotNull(message = "cost is mandatory")
	private Integer Cost;


	private Set<Integer> seatNo;

	@Pattern(regexp = "[0-9]+", message = "userId should be valid")
	private String userId;

	private Set<String> passenger = new HashSet<String>();

}
