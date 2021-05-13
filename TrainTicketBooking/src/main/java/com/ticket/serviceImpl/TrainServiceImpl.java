/**
 * 
 */
package com.ticket.serviceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ticket.dto.TrainRequestDto;
import com.ticket.dto.TrainResponseDto;
import com.ticket.exception.RecordAlreadyExist;
import com.ticket.exception.RecordNotFoundException;
import com.ticket.model.Train;
import com.ticket.repository.TrainRepository;
import com.ticket.service.TrainService;

/**
 * @author SUDESHA
 *
 */

@Service
public class TrainServiceImpl implements TrainService {

	@Autowired
	TrainRepository trainRepository;

	static Logger logger = Logger.getLogger(TrainServiceImpl.class);

	@Override
	public void saveTrainDetails(@Valid TrainRequestDto trainRequestDto) throws RecordAlreadyExist {

		try {
			Train train = new Train();
			BeanUtils.copyProperties(trainRequestDto, train);
			trainRepository.save(train);
		} catch (ConstraintViolationException e) {
			// TODO: handle exception
			logger.error("Train Number Already Exist ..");
			throw new RecordAlreadyExist("Train Number Already Exist ..");
		}
	}

	@Override
	public List<TrainResponseDto> getAllTrains(int pageNumber, int pageSize) throws RecordNotFoundException {
		Pageable page = PageRequest.of(pageNumber, pageSize);
		List<Train> trainList = trainRepository.findAll(page).getContent();
		List<TrainResponseDto> trainDtos = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(trainList)) {
			trainList.forEach(train -> {
				TrainResponseDto trainDto = new TrainResponseDto();
				BeanUtils.copyProperties(train, trainDto);
				DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
				String date1 = d.format(train.getDate());
				trainDto.setDate(date1);
				trainDtos.add(trainDto);
			});
		} else {
			logger.error("NO any data Found ..");
			throw new RecordNotFoundException("NO any data Found");
		}
		return trainDtos;
	}

	@Override
	public Train getTrainById(Long trainId) throws RecordNotFoundException {
		Optional<Train> train = trainRepository.findById(trainId);
		if (train.isPresent()) {
			return train.get();
		} else {
			throw new RecordNotFoundException("Invalid Train id : " + trainId);
		}

	}

	@Override
	public void updateTrain(Long trainId, @Valid TrainRequestDto traindata) throws RecordNotFoundException {
		Train train = trainRepository.findById(trainId).orElse(null);
		if (ObjectUtils.isNotEmpty(train)) {
			BeanUtils.copyProperties(traindata, train);
			train.setTrainNumber(traindata.getTrainNumber());
			train.setTrainName(traindata.getTrainName());
			train.setSource(traindata.getSource());
			train.setDestination(traindata.getDestination());
			train.setDuration(traindata.getDuration());
			train.setAvailableSeats(traindata.getAvailableSeats());
			train.setDate(traindata.getDate());
			trainRepository.save(train);
		} else {
			logger.error("Invalid train Id ..");
			throw new RecordNotFoundException("Invalid Train id : " + trainId);
		}

	}

	@Override
	public List<TrainResponseDto> searchSourceAndDestAndDate(String source, String destination, Date date)
			throws RecordNotFoundException {
		List<Train> trainList = trainRepository.findBySourceContainsAndDestinationContainsAndDate(source, destination,
				date);
		List<TrainResponseDto> trainDtos = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(trainList)) {
			trainList.forEach(train -> {
				TrainResponseDto trainDto = new TrainResponseDto();
				BeanUtils.copyProperties(train, trainDto);
				DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
				String date1 = d.format(train.getDate());
				trainDto.setDate(date1);
				trainDtos.add(trainDto);
			});
		} else {
			logger.error("Invalid Source , Destination ,date..");
			throw new RecordNotFoundException(
					"Invalid Source : " + source + " , Destination : " + destination + ", Date : " + date);
		}

		return trainDtos;
	}

	@Override
	public Train getTicketByTrainNumber(String trainNumber) {
		Train train = trainRepository.getTicketByTrainNumber(trainNumber);
		if (ObjectUtils.isNotEmpty(train)) {
			return train;
		} else {
			logger.error("Invalid train Number ..");
			throw new RecordNotFoundException("Invalid train Number  : " + trainNumber);
		}

	}

	@Override
	public void updateTrainByNoOfSeats(Long trainId, int noOfSeats) {
		trainRepository.updateTrainByNoOfSeats(trainId, noOfSeats);
	}

	@Override
	public List<TrainResponseDto> getAllTrainsDetails() {
		List<Train> trainList = trainRepository.findAll();
		List<TrainResponseDto> trainDtos = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(trainList)) {
			trainList.forEach(train -> {
				TrainResponseDto trainDto = new TrainResponseDto();
				BeanUtils.copyProperties(train, trainDto);
				DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
				String date1 = d.format(train.getDate());
				trainDto.setDate(date1);
				trainDtos.add(trainDto);
			});
		} else {
			logger.error("NO any data Found ..");
			throw new RecordNotFoundException("NO any data Found");
		}
		return trainDtos;
	}

}
