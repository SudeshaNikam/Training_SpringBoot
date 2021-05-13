/**
 * 
 */
package com.ticket.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.ObjectUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.dto.HistoryDto;
import com.ticket.dto.TicketRequestDto;
import com.ticket.dto.TicketResponseDto;
import com.ticket.dto.UserResponseDto;
import com.ticket.exception.RecordAlreadyExist;
import com.ticket.exception.RecordNotFoundException;
import com.ticket.model.Ticket;
import com.ticket.model.Train;
import com.ticket.service.TicketService;
import com.ticket.service.TrainService;
import com.ticket.service.UserService;

/**
 * @author SUDESHA
 *
 */
@RestController
@RequestMapping("/ticket")
@Validated
public class TicketController {

	@Autowired
	TicketService ticketService;

	@Autowired
	TrainService trainService;

	@Autowired
	UserService userService;

	static Logger logger = Logger.getLogger(TrainController.class);

	/**
	 * 
	 * @param ticketRequestDto
	 * @return
	 */
	@PostMapping("")
	public ResponseEntity<String> saveTicketBookingDetails(@RequestBody @Valid TicketRequestDto ticketRequestDto)
			throws RecordAlreadyExist {
		try {

			// Check User Id Exist
			Boolean isuserId = true;
			List<UserResponseDto> userDto = userService.getAllUserDetails();
			Long userId1 = Long.parseLong(ticketRequestDto.getUserId());
			for (UserResponseDto userdto1 : userDto) {
				if (userdto1.getUserId() == userId1) {
					logger.info("User Id Exist");
					isuserId = false;
				}
			}

			// check Train Id exist
			boolean isTrainNumber = true;
			Train train = trainService.getTicketByTrainNumber(ticketRequestDto.getTrainNumber());
			if (ObjectUtils.isNotEmpty(train)) {
				DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
				String date1 = d.format(ticketRequestDto.getDateOfJourney());
				String date2 = d.format(train.getDate());
				if (train.getTrainNumber().equals(ticketRequestDto.getTrainNumber()) && date2.equals(date1)
						&& train.getFare() == ticketRequestDto.getCost()) {
					logger.info("Train Id exist");
					isTrainNumber = false;
				}
			}

			// Seats are Available or not

			if (ObjectUtils.isNotEmpty(train)) {
				if (train.getAvailableSeats() < ticketRequestDto.getSeatNo().size()) {
					logger.error("Seats are not available");
					return new ResponseEntity<String>("Seats are not available :", HttpStatus.BAD_REQUEST);
				}
			}

			// seat no should not be 0

			for (Integer seatNo2 : ticketRequestDto.getSeatNo()) {
				if (seatNo2 == 0) {
					logger.error("seat not : 0 is invalid");
					return new ResponseEntity<String>("Seat no : " + seatNo2 + "is invalid", HttpStatus.BAD_REQUEST);
				}
			}

			// Check seat No is already booked
			List<Ticket> ticketList = ticketService.getAllTicketsByTrainNumber(ticketRequestDto.getTrainNumber());
			if (ObjectUtils.isNotEmpty(ticketList)) {
				for (Ticket ticket : ticketList) {
					for (Integer seatNoo : ticket.getSeatNo()) {
						for (Integer seatNo2 : ticketRequestDto.getSeatNo()) {
							if (seatNoo == seatNo2) {
								logger.error("seat No already booked");
								return new ResponseEntity<String>("Seat no : " + seatNoo + " is already Booked",
										HttpStatus.BAD_REQUEST);
							}
						}
					}
				}
			}

			// Check seatno and passenger name size is same
			Boolean isSeatNo = true;
			if (ticketRequestDto.getSeatNo().size() != 0 && ticketRequestDto.getPassenger().size() != 0) {
				if (ticketRequestDto.getSeatNo().size() == ticketRequestDto.getPassenger().size()) {
					isSeatNo = false;
					logger.info("Seatno and passenger name size should be same");
				}
			}

			if (isTrainNumber) {
				logger.error(
						"Traind No not Exist Or Date is not same for this Train number and Transaction not completed");

				return new ResponseEntity<String>(
						"Traind Number not Exist or Date is not same for this Train number, transaction not completed",
						HttpStatus.BAD_REQUEST);
			} else if (isuserId) {
				logger.error("UserId No not Exist");
				return new ResponseEntity<String>("User Id not Exist", HttpStatus.BAD_REQUEST);
			} else if (isSeatNo) {
				logger.error("Seatno and passenger name size should be same");

				return new ResponseEntity<String>(
						"Seat no & Passenger should not be null And Seat no & Passenger size sould be same",
						HttpStatus.BAD_REQUEST);
			} else {
				// Update seat no in train table
				int NoOfSeats = train.getAvailableSeats() - ticketRequestDto.getSeatNo().size();
				trainService.updateTrainByNoOfSeats(train.getTrainId(), NoOfSeats);
				logger.info("Successfully update available seat no in train table");
				// Save Ticket details
				ticketService.saveTicketDetails(ticketRequestDto);
				logger.info("Successfully Booked Ticket Details");
				return new ResponseEntity<String>("Successfully Booked Ticket Details", HttpStatus.CREATED);
			}

		} catch (

		Exception e) {
			logger.error("Error while Saving Ticket details" + e.getMessage(), e);
			HttpHeaders headers = new HttpHeaders();
			headers.add("error", "Error While Saved Ticket Details : " + e.getMessage());
			return new ResponseEntity<String>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws RecordNotFoundException
	 */
	@GetMapping(" ")
	public List<HistoryDto> getHistoryTransaction(
			@Valid @Pattern(regexp = "[0-9]+", message = "userId should be valid") @RequestParam String userId)
			throws RecordNotFoundException {
		return ticketService.getHistoryTransaction(userId);
	}

	/**
	 * 
	 * @return
	 * @throws RecordNotFoundException
	 */
	@GetMapping("/getAllTicketDetails")
	public List<TicketResponseDto> getAllTicketDetails(@RequestParam int pageNumber, @RequestParam int pageSize)
			throws RecordNotFoundException {
		return ticketService.getAllTicketDetails(pageNumber, pageSize);
	}

}
