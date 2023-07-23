package br.com.animelistor.util;

import br.com.animelistor.domain.Anime;

public class AnimeCreator {
	
	public static Anime createAnimeToBeSaved() {
		return new Anime("Hajime no Ippo");
	}
	
	public static Anime createAnimeValid() {
		return new Anime("Hajime no Ippo", 1L);
	}
	
	public static Anime createValidAnimeUpdated() {
		return new Anime("Hajime no Ippo 2", 1L);
	}

}
