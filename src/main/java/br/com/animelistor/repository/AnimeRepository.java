package br.com.animelistor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.animelistor.domain.Anime;


public interface AnimeRepository extends JpaRepository<Anime, Long> {

	

}
