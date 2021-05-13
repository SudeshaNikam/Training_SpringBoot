/**
 * 
 */
package com.ticket.exception;

/**
 * @author SUDESHA
 *
 */
public class RecordAlreadyExist extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param message
	 */
	public RecordAlreadyExist(String message) {
		super(message);
	}

}
