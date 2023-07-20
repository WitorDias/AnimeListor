package br.com.animelistor.exception;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BadRequestExceptionCustomizedFields {

	private LocalDateTime timestamp;
	private int status;
	private String errorDetails;
	private String developerNote;
	private String message;
	

}
