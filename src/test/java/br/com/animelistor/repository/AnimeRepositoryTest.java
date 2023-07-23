package br.com.animelistor.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.animelistor.domain.Anime;
import br.com.animelistor.util.AnimeCreator;
import jakarta.validation.ConstraintViolationException;


@DataJpaTest
@DisplayName("Tests for AnimeRepository.")
class AnimeRepositoryTest {

	@Autowired
	private AnimeRepository animeRepository;

	@Test
	@DisplayName("Save persists anime when successful.")
	void save_PersistAnime_whenSuccessful() {

		Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

		Anime animeSaved = animeRepository.save(animeToBeSaved);

		Assertions.assertThat(animeSaved).isNotNull();

		Assertions.assertThat(animeSaved.getId()).isNotNull();

		Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());

	}

	@Test
	@DisplayName("Save update anime when successful.")
	void update_UpdateAnime_whenSuccessful() {

		Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

		Anime animeSaved = animeRepository.save(animeToBeSaved);

		animeSaved.setName("Naruto");

		Anime animeUpdated = animeRepository.save(animeSaved);

		animeRepository.save(animeUpdated);

		Assertions.assertThat(animeUpdated).isNotNull();
		
		Assertions.assertThat(animeUpdated.getId()).isNotNull();
		
		Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());

	}

	@Test
	@DisplayName("Delete removes anime when successful.")
	void delete_DeleteAnime_whenSuccessful() {

		Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

		animeRepository.save(animeToBeSaved);

		animeRepository.delete(animeToBeSaved);

		Optional<Anime> animeToBeDeleted = animeRepository.findById(animeToBeSaved.getId());

		Assertions.assertThat(animeToBeDeleted).isEmpty();

	}

	@Test
	@DisplayName("FindByName returns a list of anime when successful.")
	void findByName_ReturnListOfAnime_whenSuccessful() {

		Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

		Anime animeToFind = animeRepository.save(animeToBeSaved);

		List<Anime> animeFounded = animeRepository.findByName(animeToFind.getName());

		Assertions.assertThat(animeFounded).isNotEmpty().contains(animeToFind);

	}

	@Test
	@DisplayName("FindByName returns empty list of anime when no anime is founded.")
	void findByName_ReturnsEmptyList_whenNotSuccessful() {

		List<Anime> animeFounded = animeRepository.findByName("orororororororoo");

		Assertions.assertThat(animeFounded).isEmpty();

	}
	
	@Test
	@DisplayName("Save throws ConstraintViolationException when anime name is empty.")
	void save_ThrowsConstraintViolationException_whenAnimeNameIsEmpty() {

		Anime anime = new Anime();
		
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
		.isThrownBy(() -> animeRepository.save(anime))
		.withMessageContaining("Anime name can't be empty or null");

	}


}
