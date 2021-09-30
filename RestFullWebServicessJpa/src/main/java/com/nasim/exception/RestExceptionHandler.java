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

import com.nasim.response.Response;

@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

     // internal exception
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		Response exceptionResponse = new Response(new Date(),INTERNAL_SERVER_ERROR.value(),"Internal Server Exception");
		return new ResponseEntity<Object>(exceptionResponse, INTERNAL_SERVER_ERROR);
	}

	// user not found exception
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
		Response exceptionResponse = new Response(new Date(),NOT_FOUND.value(),"User Not Found Exception");
		return new ResponseEntity<Object>(exceptionResponse, NOT_FOUND);
	}

	// form field validation
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Response exceptionResponse = new Response(new Date(),BAD_REQUEST.value(),"Bad Request ");

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

	 @ExceptionHandler(AccessDeniedException.class)
	    public ResponseEntity<Response> accessDeniedException() {
		 Response exceptionResponse = new Response(new Date(),FORBIDDEN.value(),"NOT_ENOUGH_PERMISSION ");
	        return new ResponseEntity<Response>(exceptionResponse, FORBIDDEN);
	    }
	 
	 @ExceptionHandler(TokenExpiredException.class)
	    public ResponseEntity<Response> tokenExpiredException(TokenExpiredException exception) {
		 Response exceptionResponse = new Response(new Date(),UNAUTHORIZED.value(),"Token is expired  ");
	        return new ResponseEntity<Response>(exceptionResponse, UNAUTHORIZED);
	    }
	 
	 @ExceptionHandler(NoResultException.class)
	    public ResponseEntity<Response> notFoundException(NoResultException exception) {
		 Response exceptionResponse = new Response(new Date(),NOT_FOUND.value(),"NOT_FOUND ");
	        return new ResponseEntity<Response>(exceptionResponse, NOT_FOUND);
	    }
}
