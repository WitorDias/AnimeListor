package br.com.animelistor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.animelistor.domain.Anime;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {

	List<Anime> findByName(String name);

	List<Anime> findByNameAndId(String name, Long id);

}
