package br.com.animelistor.exception;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BadRequestExceptionCustomizedFields {

	private LocalDate timestamp;
	private int status;
	private String errorDetails;
	private String developerNote;
	private String message;
	

}
