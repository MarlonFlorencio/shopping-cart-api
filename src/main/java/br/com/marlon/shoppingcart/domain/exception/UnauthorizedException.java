package br.com.marlon.shoppingcart.domain.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException {

    private static final long serialVersionUID = 1L;

    public UnauthorizedException(String exception) {
        super(exception);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.UNAUTHORIZED;
    }

}
