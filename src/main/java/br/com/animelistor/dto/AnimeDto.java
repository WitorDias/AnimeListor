package br.com.animelistor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnimeDto {

	
	@NotEmpty(message = "Anime name can't be empty or null")
	@Schema(description = "This is the anime name.", example = "Shingeki no Kyojin")
	String name;
	
	@Schema(hidden = true)
	Long id;
	
	public AnimeDto(String name){
		this.name = name;
	}


}
