package br.com.animelistor.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.animelistor.domain.Anime;
import br.com.animelistor.dto.AnimeDto;
import br.com.animelistor.exception.BadRequestException;
import br.com.animelistor.repository.AnimeRepository;
import br.com.animelistor.util.AnimeCreator;
import br.com.animelistor.util.AnimeDtoCreator;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {
	
	@InjectMocks
	private AnimeService animeService;
	@Mock
	private AnimeRepository animeRepositoryMock;
	
	
	@BeforeEach
	void setUp() {
		
		PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createAnimeValid()));
		
		BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
			.thenReturn(animePage);
		
		BDDMockito.when(animeRepositoryMock.findAll())
			.thenReturn(List.of(AnimeCreator.createAnimeValid()));
		
		BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
			.thenReturn(Optional.of(AnimeCreator.createAnimeValid()));
		
		BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
		.thenReturn(List.of(AnimeCreator.createAnimeValid()));
		
		BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
		.thenReturn(AnimeCreator.createAnimeValid());
		
		BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
		
	}

	@Test
	@DisplayName("ListAllAnimes returns a list of animes inside a page object when successful.")
	void listAllAnimes_ReturnsListOfAnimesInsedPageObject_WhenSuccessful() {
		
		String expectedName = AnimeCreator.createAnimeValid().getName();
		
		Page<AnimeDto> animePage = animeService.listAllAnimes(PageRequest.of(0, 1));
		
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
		
		List<AnimeDto> animeList = animeService.listAllAnimesNonPageable();
		
		Assertions.assertThat(animeList)
		.isNotNull()
		.isNotEmpty()
		.hasSize(1);
		
		Assertions.assertThat(animeList.get(0).getName())
		.isEqualTo(expectedListOfAnime);
	
	}
	
	@Test
	@DisplayName("ListAnimeByIdOrThrowException return a anime when successful.")
	void listAnimeByIdOrThrowException_ReturnAnimeById_WhenSuccessful() {
		
		Long expectedAnimeId = AnimeCreator.createAnimeValid().getId();
		
		Anime anime = animeService.listAnimeByIdOrThrowException(1);
		
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
		
		List<AnimeDto> animeList = animeService.listAnimeByName("One");
		
		Assertions.assertThat(animeList)
		.isNotNull()
		.isNotEmpty()
		.hasSize(1);
		
		Assertions.assertThat(animeList.get(0).getName())
		.isNotNull()
		.isEqualTo(expectedAnimeName);
		
	}
	
	@Test
	@DisplayName("listAnimeByName return a emptylist when not successful.")
	void listAnimeByName_ReturnsEmptyList_WhenNotSuccessful() {
		
		BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList());
		
		List<AnimeDto> animeList = animeService.listAnimeByName("Hajime");
		
		Assertions.assertThat(animeList)
		.isNotNull()
		.isEmpty();
		
		
	}
	
	@Test
	@DisplayName("SaveAnime return a anime when successful.")
	void saveAnime_ReturnAnime_WhenSuccessful() {
		
		Anime animeDto = animeService.saveAnime(AnimeDtoCreator.createAnimeDtoValid());
		
		Assertions.assertThat(animeDto)
		.isNotNull()
		.isEqualTo(AnimeCreator.createAnimeValid());
		
	}
	
	@Test
	@DisplayName("ReplaceAnime updates a anime when successful.")
	void replaceAnime_UpdatesAnime_WhenSuccessful() {
		
		Assertions.assertThatCode(() -> animeService.replaceAnime(AnimeDtoCreator.createValidAnimeDtoUpdated()))
		.doesNotThrowAnyException();
		
	}
	
	@Test
	@DisplayName("DeleteAnime removes a anime when successful.")
	void deleteAnime_RemovesAnime_WhenSuccessful() {
		
		Assertions.assertThatCode(() -> animeService.deleteAnime(1))
		.doesNotThrowAnyException();
		
	}
	
	@Test
	@DisplayName("ListAnimeByIdOrThrowException throws a bad request exception when anime is not found.")
	void listAnimeByIdOrThrowException_ThrowsBadRequestException_WhenAnimeIsNotFound() {
		
		BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.empty());
		
		Assertions.assertThatExceptionOfType(BadRequestException.class)
		.isThrownBy(() -> animeService.listAnimeByIdOrThrowException(1));
			
	}


}
