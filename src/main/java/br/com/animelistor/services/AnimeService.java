package br.com.animelistor.services;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.animelistor.domain.Anime;
import br.com.animelistor.dto.AnimeDto;
import br.com.animelistor.exception.BadRequestException;
import br.com.animelistor.repository.AnimeRepository;
import jakarta.transaction.Transactional;

@Service
public class AnimeService {
	
	private final AnimeRepository animeRepository;
	
	
	@Autowired
	public AnimeService(AnimeRepository animeRepository) {
		this.animeRepository = animeRepository;
	}

	public Page<AnimeDto> listAllAnimes(Pageable pageable) {
		
		 Page<Anime> animePage = animeRepository.findAll(pageable);
		return animePage.map(animePaged -> new AnimeDto(animePaged.getName(),animePaged.getId()));
		
	}
	
	public List<AnimeDto> listAnimeByName(String name) {
		
		return animeRepository.findByName(name).stream()
				.map(anime -> new AnimeDto(anime.getName(),anime.getId())).toList();

	}
	
	public Anime listAnimeByIdOrThrowException(long id) {
		
		return animeRepository.findById(id)
				.orElseThrow(
						() -> new BadRequestException("Anime not found"));
				
	}
	
	@Transactional
	public Anime saveAnime(AnimeDto animeDto) {
		
		Anime anime = new Anime();
		BeanUtils.copyProperties(animeDto, anime);
		return animeRepository.save(anime);
		
	}
	
	public void replaceAnime(AnimeDto animeDto) {
		
		listAnimeByIdOrThrowException(animeDto.getId());
		Anime anime = new Anime();
		BeanUtils.copyProperties(animeDto, anime);
		animeRepository.save(anime);
		
	}
	
	public void deleteAnime (long id) {
		
		animeRepository.delete(listAnimeByIdOrThrowException(id));

	}

	public List<AnimeDto> listAllAnimesNonPageable() {
		
		return animeRepository.findAll().stream()
		.map(anime -> new AnimeDto(anime.getName(),anime.getId()))
		.toList();
	}

}
