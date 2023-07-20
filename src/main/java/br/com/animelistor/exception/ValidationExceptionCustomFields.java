package br.com.animelistor.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionCustomFields extends ExceptionCustomFields{
	
	private final String fields;
	private final String fieldsMessage;

}
