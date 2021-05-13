/**
 * 
 */
package com.ticket.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
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
@Entity
@Table(name = "TICKET")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
	@Id
	@Column(name = "ID", length = 64, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ticketId;

	@NotBlank(message = "ticketNumber is mandatory")
	@Column(name = "TICKET_NUMBER", unique = true, nullable = false)
	private String ticketNumber;

	@NotBlank(message = "trainNumber is mandatory")
	@Column(name = "TRAIN_NUMBER", nullable = false,length = 150)
	private String trainNumber;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DATE_JOURNEY", nullable = false)
	@Future
	private Date dateOfJourney;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DATE_BOOKING", nullable = false)
	private Date dateOfBooking;

	@Min(value = 10, message = "must be equal or greater than 10")
	@NotNull(message = "Cost is mandatory")
	@Column(name = "COST", nullable = false)
	private Integer Cost;

	@ElementCollection
	@CollectionTable(name = "TICKET_SEATNO", joinColumns = @JoinColumn(name = "TICKET_ID"))
	private Set<Integer> seatNo;

	@Column(name = "USER_ID", nullable = false)
	@Pattern(regexp = "[0-9]+", message = "userId should be valid")
	private String userId;

	@ElementCollection
	@CollectionTable(name = "TICKET_PASSENGER", joinColumns = @JoinColumn(name = "TICKET_ID"))
	private Set<String> passenger = new HashSet<String>();
}
