/**
 * 
 */
package com.ticket.model;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Entity
@Table(name = "TRAIN")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Train {
	@Id
	@Column(name = "ID", length = 64, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long trainId;

	@NotBlank(message = "trainNumber is mandatory")
	@Column(name = "TRAIN_NUMBER", unique = true, length = 150)
	private String trainNumber;

	@Column(name = "TRAIN_NAME")
	private String trainName;

	@Pattern(regexp = "[a-zA-z]+", message = "source should be valid")
	@NotBlank(message = "source is mandatory")
	@Column(name = "SOURCE", nullable = false)
	private String source;

	@Pattern(regexp = "[a-zA-z]+", message = "destination should be valid")
	@NotBlank(message = "destination is mandatory")
	@Column(name = "DESTINATION", nullable = false)
	private String destination;

	@Column(name = "DURATION")
	private Time duration;

	@Range(min = 50, max = 500)
	@Column(name = "AVAILABLE_SEATS", nullable = false)
	private int availableSeats;

	@Min(value = 10, message = "must be equal or greater than 10")
	@Column(name = "Fare", nullable = false)
	private int fare;

	@Column(name = "DATE")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Future
	private Date date;

}
