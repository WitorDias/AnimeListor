package br.com.animelistor.exception;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ExceptionCustomFields {
	
	protected LocalDateTime timestamp;
	protected int status;
	protected String errorDetails;
	protected String developerNote;
	protected String message;

}
