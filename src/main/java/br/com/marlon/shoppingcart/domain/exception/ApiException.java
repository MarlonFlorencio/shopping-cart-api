package br.com.marlon.shoppingcart.domain.exception;

import org.springframework.http.HttpStatus;

public abstract class ApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApiException(String exception) {
		super(exception);
	}

	abstract public HttpStatus getStatusCode();
	
}
