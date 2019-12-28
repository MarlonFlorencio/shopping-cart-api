package br.com.marlon.shoppingcart.domain.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String exception) {
		super(exception);
	}

	@Override
	public HttpStatus getStatusCode() {
		return HttpStatus.NOT_FOUND;
	}

}
