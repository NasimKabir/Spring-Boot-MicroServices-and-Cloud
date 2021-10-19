package com.nasim.exception;

import static org.springframework.http.HttpStatus.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nasim.response.HttpResponse;

@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

     // internal exception
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		HttpResponse exceptionResponse = new HttpResponse(new Date(),INTERNAL_SERVER_ERROR.value(),"Internal Server Exception");
		return new ResponseEntity<Object>(exceptionResponse, INTERNAL_SERVER_ERROR);
	}

	// user not found exception
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
		HttpResponse exceptionResponse = new HttpResponse(new Date(),NOT_FOUND.value(),"User Not Found Exception");
		return new ResponseEntity<Object>(exceptionResponse, NOT_FOUND);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<HttpResponse> accessDeniedException() {
		HttpResponse exceptionResponse = new HttpResponse(new Date(),FORBIDDEN.value(),"NOT_ENOUGH_PERMISSION ");
		return new ResponseEntity<HttpResponse>(exceptionResponse, FORBIDDEN);
	}
	
	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exception) {
		HttpResponse exceptionResponse = new HttpResponse(new Date(),UNAUTHORIZED.value(),"Token is expired  ");
		return new ResponseEntity<HttpResponse>(exceptionResponse, UNAUTHORIZED);
	}
	
	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<HttpResponse> notFoundException(NoResultException exception) {
		HttpResponse exceptionResponse = new HttpResponse(new Date(),NOT_FOUND.value(),"NOT_FOUND ");
		return new ResponseEntity<HttpResponse>(exceptionResponse, NOT_FOUND);
	}
	// form field validation
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		HttpResponse exceptionResponse = new HttpResponse(new Date(),BAD_REQUEST.value(),"Bad Request ");

		List<FieldError> fieldError = ex.getBindingResult().getFieldErrors();
		for (FieldError error : fieldError) {
			List<FieldValidationError> fErrorList = exceptionResponse.getErrors().get(error.getField());
			if (fErrorList == null) {
				fErrorList = new ArrayList<FieldValidationError>();
				exceptionResponse.getErrors().put(error.getField(), fErrorList);
			}

			FieldValidationError vError = new FieldValidationError();
			vError.setCode(error.getCode());
			vError.setMessage(error.getDefaultMessage());
			fErrorList.add(vError);
		}

		return new ResponseEntity<Object>(exceptionResponse, BAD_REQUEST);
	}

}
