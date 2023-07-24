package br.com.animelistor.integration;


import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import br.com.animelistor.domain.Anime;
import br.com.animelistor.dto.AnimeDto;
import br.com.animelistor.repository.AnimeRepository;
import br.com.animelistor.util.AnimeCreator;
import br.com.animelistor.util.AnimeDtoCreator;
import br.com.animelistor.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AnimeControllerIT {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private AnimeRepository animeRepository;
	
	@LocalServerPort
	private int localPort;
	
	@Test
	@DisplayName("List returns a list of animes inside a page object when successful.")
	void list_ReturnsListOfAnimesInsedPageObject_WhenSuccessful() {
		
		Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		String expectedName = animeSaved.getName();
		
		PageableResponse<Anime> animePage = testRestTemplate.exchange("/api/v1/animes", HttpMethod.GET, null, 
				new ParameterizedTypeReference<PageableResponse<Anime>>() {
				}).getBody();
		
		Assertions.assertThat(animePage).isNotNull();
		
		Assertions.assertThat(animePage.toList())
			.isNotEmpty()
			.hasSize(1);
		
		Assertions.assertThat(animePage.toList()
			.get(0).getName())
			.isEqualTo(expectedName);
		
	}
	
	@Test
	@DisplayName("ListAllAnimesNonPageable returns a list of animes when successful.")
	void listAllAnimesNonPageable_ReturnsListOfAnimes_WhenSuccessful() {
		
		Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		String expectedName = animeSaved.getName();
		
		List<Anime> animes = testRestTemplate.exchange("/api/v1/animes/all", HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Anime>>() {
				}).getBody();
		
		Assertions.assertThat(animes)
			.isNotNull()
			.isNotEmpty()
			.hasSize(1);
		
		Assertions.assertThat(animes.get(0).getName())
			.isEqualTo(expectedName);
	
	}
	
	@Test
	@DisplayName("ListAnimeById return a anime when successful.")
	void listAnimeById_ReturnAnimeById_WhenSuccessful() {
		
		Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
		Long expectedAnimeId = animeSaved.getId();
		
		Anime anime = testRestTemplate.getForObject("/api/v1/animes/{id}", Anime.class, expectedAnimeId);
		
		Assertions.assertThat(anime)
			.isNotNull();
		
		Assertions.assertThat(anime.getId())
			.isNotNull()
			.isEqualTo(expectedAnimeId);
		
	}
	
	@Test
	@DisplayName("ListAnimeByName returns a list of anime when successful.")
	void listAnimeByName_ReturnAnimeByName_WhenSuccessful() {
		
		Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
		String expectedAnimeName = animeSaved.getName();
		
		String url = String.format("/api/v1/animes/findByName?name=%s", expectedAnimeName);
		
		List<Anime> animeList = testRestTemplate.exchange(url, HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Anime>>() {
				}).getBody();
		
		Assertions.assertThat(animeList)
			.isNotNull()
			.isNotEmpty()
			.hasSize(1);
		
		Assertions.assertThat(animeList.get(0).getName())
			.isNotNull()
			.isEqualTo(expectedAnimeName);
		
	}
	
	@Test
	@DisplayName("listAnimeByName return a empty list when not successful.")
	void listAnimeByName_ReturnsEmptyList_WhenNotSuccessful() {
		
		String url = "/api/v1/animes/findByName?name=ororororo";
		
		List<Anime> animeList = testRestTemplate.exchange(url, HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Anime>>() {
				}).getBody();
		
		Assertions.assertThat(animeList)
			.isNotNull()
			.isEmpty();
		
	}
	
	@Test
	@DisplayName("CreateAnime return a anime when successful.")
	void createAnime_ReturnAnime_WhenSuccessful() {
		AnimeDto animeDtoToBeSaved = AnimeDtoCreator.createAnimeDtoValid();
		
		ResponseEntity<Anime> animeResponseEntity = testRestTemplate.postForEntity("/api/v1/animes", animeDtoToBeSaved, Anime.class);
		
		Assertions.assertThat(animeResponseEntity)
			.isNotNull();
		
		Assertions.assertThat(animeResponseEntity.getStatusCode())
			.isEqualTo(HttpStatus.CREATED);
		
		Assertions.assertThat(animeResponseEntity.getBody())
			.isNotNull();
		
		Assertions.assertThat(animeResponseEntity.getBody().getId())
			.isNotNull();
		
	}
	
	@Test
	@DisplayName("ReplaceAnime updates a anime when successful.")
	void replaceAnime_UpdatesAnime_WhenSuccessful() {

		Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
		animeSaved.setName("My Hero Academy");
		
		ResponseEntity<Void> entity = testRestTemplate.exchange("/api/v1/animes", HttpMethod.PUT, new HttpEntity<>(animeSaved), Void.class);
		
		Assertions.assertThat(entity)
			.isNotNull();
		
		Assertions.assertThat(entity.getStatusCode())
			.isEqualTo(HttpStatus.NO_CONTENT);
		
	}
	
	@Test
	@DisplayName("DeleteAnime removes a anime when successful.")
	void deleteAnime_RemovesAnime_WhenSuccessful() {
	
		Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
		ResponseEntity<Void> animeToDelete = testRestTemplate.exchange("/api/v1/animes/{id}", HttpMethod.DELETE, null, Void.class, animeSaved.getId());
				
		Assertions.assertThat(animeToDelete)
			.isNotNull();
		
		Assertions.assertThat(animeToDelete.getStatusCode())
			.isEqualTo(HttpStatus.NO_CONTENT);
		
	}
}
