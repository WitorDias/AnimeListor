package br.com.animelistor.controllers;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.animelistor.domain.Anime;
import br.com.animelistor.dto.AnimeDto;
import br.com.animelistor.services.AnimeService;
import br.com.animelistor.util.AnimeCreator;
import br.com.animelistor.util.AnimeDtoCreator;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

	@InjectMocks
	private AnimeController animeController;
	@Mock
	private AnimeService animeServiceMock;
	
	
	@BeforeEach
	void setUp() {
		
		PageImpl<AnimeDto> animePage = new PageImpl<>(List.of(AnimeDtoCreator.createAnimeDtoValid()));
		
		BDDMockito.when(animeServiceMock.listAllAnimes(ArgumentMatchers.any()))
			.thenReturn(animePage);
		
		BDDMockito.when(animeServiceMock.listAllAnimesNonPageable())
			.thenReturn(List.of(AnimeDtoCreator.createAnimeDtoValid()));
		
		BDDMockito.when(animeServiceMock.listAnimeByIdOrThrowException(ArgumentMatchers.anyLong()))
			.thenReturn(AnimeCreator.createAnimeValid());
		
		BDDMockito.when(animeServiceMock.listAnimeByName(ArgumentMatchers.anyString()))
		.thenReturn(List.of(AnimeDtoCreator.createAnimeDtoValid()));
		
		BDDMockito.when(animeServiceMock.saveAnime(ArgumentMatchers.any(AnimeDto.class)))
		.thenReturn(AnimeCreator.createAnimeValid());
		
		BDDMockito.doNothing().when(animeServiceMock).replaceAnime(ArgumentMatchers.any(AnimeDto.class));
		
		BDDMockito.doNothing().when(animeServiceMock).deleteAnime(ArgumentMatchers.anyLong());
		
	}

	@Test
	@DisplayName("List returns a list of animes inside a page object when successful.")
	void list_ReturnsListOfAnimesInsedPageObject_WhenSuccessful() {
		
		String expectedName = AnimeCreator.createAnimeValid().getName();
		
		Page<AnimeDto> animePage = animeController.listAnime(null).getBody();
		
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
		
		String expectedListOfAnime = AnimeCreator.createAnimeValid().getName();
		
		List<AnimeDto> animeList = animeController.listAnimeNonPageable().getBody();
		
		
		Assertions.assertThat(animeList)
		.isNotNull()
		.isNotEmpty()
		.hasSize(1);
		
		Assertions.assertThat(animeList.get(0).getName())
		.isEqualTo(expectedListOfAnime);
	
	}
	
	@Test
	@DisplayName("ListAnimeById return a anime when successful.")
	void listAnimeById_ReturnAnimeById_WhenSuccessful() {
		
		Long expectedAnimeId = AnimeCreator.createAnimeValid().getId();
		
		Anime anime = animeController.listAnimeById(1).getBody();
		
		Assertions.assertThat(anime)
		.isNotNull();
		
		Assertions.assertThat(anime.getId())
		.isNotNull()
		.isEqualTo(expectedAnimeId);
		
	}
	
	@Test
	@DisplayName("ListAnimeByName returns a list of anime when successful.")
	void listAnimeByName_ReturnAnimeByName_WhenSuccessful() {
		
		String expectedAnimeName = AnimeCreator.createAnimeValid().getName();
		
		List<AnimeDto> animeList = animeController.listAnimeByName("One").getBody();
		
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
		
		BDDMockito.when(animeServiceMock.listAnimeByName(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList());
		
		List<AnimeDto> animeList = animeController.listAnimeByName("Hajime").getBody();
		
		Assertions.assertThat(animeList)
		.isNotNull()
		.isEmpty();
		
		
	}
	
	@Test
	@DisplayName("CreateAnime return a anime when successful.")
	void createAnime_ReturnAnime_WhenSuccessful() {
		
		Anime animeDto = animeController.createAnime(AnimeDtoCreator.createAnimeDtoValid()).getBody();
		
		Assertions.assertThat(animeDto)
		.isNotNull()
		.isEqualTo(AnimeCreator.createAnimeValid());
		
	}
	
	@Test
	@DisplayName("ReplaceAnime updates a anime when successful.")
	void replaceAnime_UpdatesAnime_WhenSuccessful() {
		
		ResponseEntity<Void> entity = animeController.replaceAnime(AnimeDtoCreator.createValidAnimeDtoUpdated());
		
		Assertions.assertThatCode(() -> animeController.replaceAnime(AnimeDtoCreator.createValidAnimeDtoUpdated()))
		.doesNotThrowAnyException();
		
		Assertions.assertThat(entity).isNotNull();
		
		Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		
	}
	
	@Test
	@DisplayName("DeleteAnime removes a anime when successful.")
	void deleteAnime_RemovesAnime_WhenSuccessful() {
		
		ResponseEntity<Void> animeToDelete = animeController.deleteAnime(1);
		
		Assertions.assertThatCode(() -> animeController.deleteAnime(1))
		.doesNotThrowAnyException();
		
		Assertions.assertThat(animeToDelete).isNotNull();
		
		Assertions.assertThat(animeToDelete.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		
	}

}
