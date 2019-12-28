package br.com.marlon.shoppingcart.domain.exception;

import org.springframework.http.HttpStatus;

public class InvalidJwtAuthenticationException extends ApiException {

	private static final long serialVersionUID = 1L;
	
	public InvalidJwtAuthenticationException(String exception) {
		super(exception);
	}

	@Override
	public HttpStatus getStatusCode() {
		return HttpStatus.BAD_REQUEST;
	}

}
