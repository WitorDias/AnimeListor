package br.com.animelistor.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.animelistor.exception.BadRequestException;
import br.com.animelistor.exception.BadRequestExceptionCustomizedFields;
import br.com.animelistor.exception.ValidationExceptionCustomFields;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<BadRequestExceptionCustomizedFields> handlerBadRequestException(
			BadRequestException bre) {

		return new ResponseEntity<>(
				BadRequestExceptionCustomizedFields.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.errorDetails("Bad Request Exception. Check the Documentation for more info")
				.developerNote(bre.getClass().getName())
				.message(bre.getMessage())
				.build(), HttpStatus.BAD_REQUEST);
		
	}
	
	//this method improve field validation visibility from validation dependency (@Valid)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationExceptionCustomFields> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception){
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
		String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
		
		return new ResponseEntity<>(
				ValidationExceptionCustomFields.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.errorDetails("Bad Request Exception. Invalid fields.")
				.developerNote(exception.getClass().getName())
				.message("Check if some fields may have invalid inputs.")
				.fields(fields)
				.fieldsMessage(fieldsMessage)
				.build(), HttpStatus.BAD_REQUEST);
	}


}
