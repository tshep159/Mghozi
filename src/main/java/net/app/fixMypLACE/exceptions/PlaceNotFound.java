package net.app.fixMypLACE.exceptions;

import java.io.Serializable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author lenovo
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No Place found!")
public class PlaceNotFound extends RuntimeException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private String province;

	public PlaceNotFound() {
		this("Sorry please try again.");
	}

	public PlaceNotFound(String message) {
		this.message = message;
	}

	public PlaceNotFound(String message, String province) {
		this.message = message;
		this.province = province;
	}

	public String getMessage() {
		return message;
	}

	public String getProvince() {
		return province;
	}

}