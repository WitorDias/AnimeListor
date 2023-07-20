package br.com.animelistor.handler;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.animelistor.exception.BadRequestException;
import br.com.animelistor.exception.BadRequestExceptionCustomizedFields;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<BadRequestExceptionCustomizedFields> HandlerBadRequestException(
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

}
