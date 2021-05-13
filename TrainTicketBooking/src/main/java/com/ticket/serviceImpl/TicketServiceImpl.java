/**
 * 
 */
package com.ticket.serviceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ticket.dto.HistoryDto;
import com.ticket.dto.TicketRequestDto;
import com.ticket.dto.TicketResponseDto;
import com.ticket.exception.RecordAlreadyExist;
import com.ticket.exception.RecordNotFoundException;
import com.ticket.model.Ticket;
import com.ticket.repository.TicketRepository;
import com.ticket.service.TicketService;

/**
 * @author SUDESHA
 *
 */
@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	TicketRepository ticketRepository;

	static Logger logger = Logger.getLogger(TicketServiceImpl.class);

	@Override
	public void saveTicketDetails(@Valid TicketRequestDto ticketRequestDto) throws RecordAlreadyExist {
		try {
			Ticket ticket = new Ticket();
			BeanUtils.copyProperties(ticketRequestDto, ticket);
			LocalDate localDate = LocalDate.now();
			Date d1 = java.sql.Date.valueOf(localDate);
			ticket.setDateOfBooking(d1);
			ticketRepository.save(ticket);
		} catch (ConstraintViolationException e) {
			// TODO: handle exception
			logger.error("Ticket Number Already Exist ..");
			throw new RecordAlreadyExist("TicketNumber Already Exist ..");
		}
	}

	@Override
	public List<TicketResponseDto> getAllTicketDetails(int pageNumber, int pageSize) throws RecordNotFoundException {
		Pageable page = PageRequest.of(pageNumber, pageSize);
		List<Ticket> ticketList = ticketRepository.findAll(page).getContent();
		List<TicketResponseDto> ticketResponseDtos = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(ticketList)) {
			ticketList.forEach(ticket -> {
				TicketResponseDto ticketDto = new TicketResponseDto();
				BeanUtils.copyProperties(ticket, ticketDto);

				DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
				String date1 = d.format(ticket.getDateOfBooking());
				ticketDto.setDateOfBooking(date1);

				String date2 = d.format(ticket.getDateOfJourney());
				ticketDto.setDateOfJourney(date2);

				ticketResponseDtos.add(ticketDto);
			});
		} else {
			logger.error("NO any data Found ..");
			throw new RecordNotFoundException("NO any data Found");
		}
		return ticketResponseDtos;
	}

	@Override
	public List<HistoryDto> getHistoryTransaction(String userId) throws RecordNotFoundException {
		List<Ticket> trainList = ticketRepository.getHistoryTransactionById(userId);
		List<HistoryDto> historyDtos = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(trainList)) {
			trainList.forEach(ticket -> {
				HistoryDto historyDto = new HistoryDto();
				BeanUtils.copyProperties(ticket, historyDto);
				DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
				String date1 = d.format(ticket.getDateOfBooking());
				String date2 = d.format(ticket.getDateOfJourney());
				historyDto.setDateOfJourney(date2);
				historyDto.setDateOfBooking(date1);
				historyDtos.add(historyDto);
			});
		} else {
			logger.error("Invalid User id " + userId);
			throw new RecordNotFoundException("Invalid User id : " + userId);
		}
		return historyDtos;
	}

	@Override
	public List<Ticket> getAllTicketsByTrainNumber(String trainNumber) throws RecordNotFoundException {

		List<Ticket> ticketList = ticketRepository.getAllTicketsByTrainNumber(trainNumber);
		if (ObjectUtils.isNotEmpty(ticketList)) {
			return ticketList;
		} else {
			logger.error("Invalid Train Number " + trainNumber);
			throw new RecordNotFoundException("Invalid Train Number : " + trainNumber);
		}

	}

}
