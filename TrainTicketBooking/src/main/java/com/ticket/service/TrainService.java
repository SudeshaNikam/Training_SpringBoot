/**
 * 
 */
package com.ticket.service;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ticket.dto.TrainRequestDto;
import com.ticket.dto.TrainResponseDto;
import com.ticket.exception.RecordAlreadyExist;
import com.ticket.exception.RecordNotFoundException;
import com.ticket.model.Train;

/**
 * @author SUDESHA
 *
 */

@Service
public interface TrainService {

	/**
	 * @param trainRequestDto
	 * @throws RecordAlreadyExist
	 */
	void saveTrainDetails(@Valid TrainRequestDto trainRequestDto) throws RecordAlreadyExist;

	/**
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 * @throws RecordNotFoundException
	 */
	List<TrainResponseDto> getAllTrains(int pageNumber, int pageSize) throws RecordNotFoundException;

	/**
	 * @param trainId
	 * @return
	 * @throws RecordNotFoundException
	 */
	Train getTrainById(Long trainId) throws RecordNotFoundException;

	/**
	 * @param trainId
	 * @param trainRequestDto
	 * @throws RecordNotFoundException
	 */
	void updateTrain(Long trainId, TrainRequestDto trainRequestDto) throws RecordNotFoundException;

	/**
	 * @param destination
	 * @param source
	 * @param date1
	 * @return
	 * @throws RecordNotFoundException
	 */
	List<TrainResponseDto> searchSourceAndDestAndDate(String source, String destination, Date date1)
			throws RecordNotFoundException;

	/**
	 * @param trainNumber
	 * @return
	 */
	Train getTicketByTrainNumber(String trainNumber);

	/**
	 * @param trainId
	 * @param noOfSeats
	 */
	void updateTrainByNoOfSeats(Long trainId, int noOfSeats);

	/**
	 * @return
	 */
	List<TrainResponseDto> getAllTrainsDetails();

}
