/**
 * 
 */
package com.ticket.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ticket.model.Train;

/**
 * @author SUDESHA
 *
 */

public interface TrainRepository extends JpaRepository<Train, Long> {

	/**
	 * @param source
	 * @param destination
	 * @param date
	 * @return
	 */
	List<Train> findBySourceContainsAndDestinationContainsAndDate(String source, String destination, Date date);

	/**
	 * @param trainNumber
	 * @return
	 */
	@Query("from Train where trainNumber=:trainNumber")
	Train getTicketByTrainNumber(@Param("trainNumber") String trainNumber);

	/**
	 * @param trainId
	 * @param noOfSeats
	 */
	@Transactional
	@Modifying
	@Query("UPDATE Train t SET t.availableSeats =:noOfSeats where t.trainId =:trainId")
	void updateTrainByNoOfSeats(@Param("trainId") Long trainId, @Param("noOfSeats") int noOfSeats);

}
