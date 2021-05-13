/**
 * 
 */
package com.ticket.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.dto.TrainRequestDto;
import com.ticket.dto.TrainResponseDto;
import com.ticket.exception.RecordNotFoundException;
import com.ticket.model.Train;
import com.ticket.service.TrainService;

/**
 * @author SUDESHA
 *
 */

@RestController
@RequestMapping("/trains")
@Validated
public class TrainController {

	@Autowired
	TrainService trainService;

	static Logger logger = Logger.getLogger(TrainController.class);

	/**
	 * 
	 * @param trainRequestDto
	 * @return
	 */
	@PostMapping(" ")
	public ResponseEntity<String> SaveTrainDetails(@RequestBody @Valid TrainRequestDto trainRequestDto) {
		try {

			List<TrainResponseDto> trainDto1 = trainService.getAllTrainsDetails();
			for (TrainResponseDto trainDto2 : trainDto1) {
				DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
				String date1 = d.format(trainRequestDto.getDate());
				if (trainDto2.getDuration().equals(trainRequestDto.getDuration()) && date1.equals(trainDto2.getDate())
						&& (trainRequestDto.getSource().equalsIgnoreCase(trainDto2.getSource()))) {
					logger.error("Train already booked for the same date , time and source");
					return new ResponseEntity<String>("Train already booked for the same date , time and source",
							HttpStatus.BAD_REQUEST);
				}
			}

			trainService.saveTrainDetails(trainRequestDto);
			logger.info("Successfully Saved Train Details");
			return new ResponseEntity<String>("Successfully Saved Train Details", HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Error while Saving train details" + e.getMessage(), e);
			HttpHeaders headers = new HttpHeaders();
			headers.add("error", "Error While Saved Train Details : " + e.getMessage());
			return new ResponseEntity<String>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * 
	 * @return
	 * @throws RecordNotFoundException
	 */
	@GetMapping("/getAllTrains")
	public List<TrainResponseDto> getAllTrain(@RequestParam int pageNumber, @RequestParam int pageSize)
			throws RecordNotFoundException {
		return trainService.getAllTrains(pageNumber, pageSize);
	}

	/**
	 * 
	 * @param trainId
	 * @param trainRequestDto
	 * @return
	 * @throws RecordNotFoundException
	 */
	@PutMapping("/{trainId}")
	public ResponseEntity<String> updateTrain(@PathVariable Long trainId,
			@RequestBody @Valid TrainRequestDto trainRequestDto) throws RecordNotFoundException {
		try {

			// check Train already arrived

			LocalDate localDate = LocalDate.now();
			Date d1 = java.sql.Date.valueOf(localDate);

			Train trainDto = trainService.getTrainById(trainId);
			if (d1.compareTo(trainDto.getDate()) > 0) {
				logger.error("Train Already arrived from this Date : " + trainDto.getDate());
				return new ResponseEntity<String>("Train Already arrived from this Date :" + " " + trainDto.getDate(),
						HttpStatus.BAD_REQUEST);
			}

			List<TrainResponseDto> trainDto1 = trainService.getAllTrainsDetails();
			for (TrainResponseDto trainDto2 : trainDto1) {
				DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
				String date1 = d.format(trainRequestDto.getDate());
				if (trainDto2.getDuration().equals(trainRequestDto.getDuration()) && date1.equals(trainDto2.getDate())
						&& (trainRequestDto.getSource().equalsIgnoreCase(trainDto2.getSource()))) {
					logger.error("Train already booked for the same date and time and source:");
					return new ResponseEntity<String>("Train already booked for the same date and time and source:",
							HttpStatus.BAD_REQUEST);
				}
			}

			trainService.updateTrain(trainId, trainRequestDto);
			logger.info("Successfully Updated Train Details");
			return new ResponseEntity<String>("Successfully Updated Train Details", HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Error while Updated train details" + e.getMessage(), e);
			HttpHeaders headers = new HttpHeaders();
			headers.add("error", "Error While Updated Train Details : " + e.getMessage());
			return new ResponseEntity<String>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 
	 * @param source
	 * @param destination
	 * @param date
	 * @return
	 * @throws RecordNotFoundException
	 */
	@GetMapping(" ")
	public ResponseEntity<List<TrainResponseDto>> getBySourceAndDestinationAndDate(
			@Valid @Pattern(regexp = "[a-zA-z]+", message = "source name should be valid") @RequestParam String source,
			@Pattern(regexp = "[a-zA-z]+", message = "destination name should be valid") @RequestParam String destination,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam @Valid Date date) throws RecordNotFoundException {

		List<TrainResponseDto> tainResponseDto = trainService.searchSourceAndDestAndDate(source, destination, date);
		return new ResponseEntity<List<TrainResponseDto>>(tainResponseDto, HttpStatus.OK);
	}

}
