package br.com.animelistor.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AnimeDto {

	Long id;
	@NotEmpty(message = "Anime name can't be empty or null")
	String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


}
