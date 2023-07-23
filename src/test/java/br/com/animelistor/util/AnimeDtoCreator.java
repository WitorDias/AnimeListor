package br.com.animelistor.util;

import br.com.animelistor.dto.AnimeDto;

public class AnimeDtoCreator {
	
	public static AnimeDto createAnimeDtoValid() {
		
		return new AnimeDto(AnimeCreator.createAnimeValid().getName());
		
	}
	
	public static AnimeDto createValidAnimeDtoUpdated() {
		
		return new AnimeDto(AnimeCreator.createValidAnimeUpdated().getName(), 
				AnimeCreator.createValidAnimeUpdated().getId());
		
	}
	
}
