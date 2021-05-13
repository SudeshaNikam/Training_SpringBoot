/**
 * 
 */
package com.ticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ticket.model.Ticket;

/**
 * @author SUDESHA
 *
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	/**
	 * @param userId
	 * @return
	 */
	@Query("from Ticket where userId=:userId")
	List<Ticket> getHistoryTransactionById(@Param("userId") String userId);

	/**
	 * @param trainId
	 * @return
	 */
	@Query("from Ticket where trainNumber=:trainNumber")
	List<Ticket> getAllTicketsByTrainNumber(String trainNumber);

}
