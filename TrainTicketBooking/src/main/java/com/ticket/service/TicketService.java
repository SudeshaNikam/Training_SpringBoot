/**
 * 
 */
package com.ticket.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ticket.dto.HistoryDto;
import com.ticket.dto.TicketRequestDto;
import com.ticket.dto.TicketResponseDto;
import com.ticket.exception.RecordAlreadyExist;
import com.ticket.exception.RecordNotFoundException;
import com.ticket.model.Ticket;

/**
 * @author SUDESHA
 *
 */
@Service
public interface TicketService {

	/**
	 * @param ticketRequestDto
	 * @throws TicketNumberAlreadyExist
	 * @throws RecordAlreadyExist 
	 */
	void saveTicketDetails(@Valid TicketRequestDto ticketRequestDto) throws RecordAlreadyExist;

	/**
	 * @param userId
	 * @return
	 * @throws RecordNotFoundException
	 */
	List<HistoryDto> getHistoryTransaction(String userId) throws RecordNotFoundException;

	/**
	 * @param string
	 * @return
	 * @throws RecordNotFoundException
	 */
	List<Ticket> getAllTicketsByTrainNumber(String trainNumber) throws RecordNotFoundException;

	/**
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 * @throws RecordNotFoundException
	 */
	List<TicketResponseDto> getAllTicketDetails(int pageNumber, int pageSize) throws RecordNotFoundException;

}
