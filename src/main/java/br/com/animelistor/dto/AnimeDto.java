package br.com.animelistor.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnimeDto {

	
	@NotEmpty(message = "Anime name can't be empty or null")
	String name;
	Long id;
	
	public AnimeDto(String name){
		this.name = name;
	}


}
