package br.com.marlon.shoppingcart.application.handler;

import br.com.marlon.shoppingcart.domain.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {

		ex.printStackTrace();

		ExceptionResponse result = getExceptionResponse(ex, request);

		HttpStatus status = ex instanceof ApiException
				? ((ApiException) ex).getStatusCode()
				: HttpStatus.INTERNAL_SERVER_ERROR;

		return new ResponseEntity<>(result, status);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public final ResponseEntity<ExceptionResponse> badCredentialsException(Exception ex, WebRequest request) {
		ExceptionResponse result = getExceptionResponse(ex, request);
		return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public final ResponseEntity<ExceptionResponse> illegalArgumentException(Exception ex, WebRequest request) {
		ExceptionResponse result = getExceptionResponse(ex, request);
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}

	private ExceptionResponse getExceptionResponse(Exception ex, WebRequest request) {
		return new ExceptionResponse(
				LocalDateTime.now(),
				ex.getMessage(),
				request.getDescription(false));
	}

}
