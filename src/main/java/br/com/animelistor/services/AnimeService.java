package br.com.animelistor.services;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

	public Page<Anime> listAllAnimes(Pageable pageable) {
		return animeRepository.findAll(pageable);
		
	}
	
	public List<Anime> listAnimeByName(String name) {
		return animeRepository.findByName(name);

	}
	
	public List<Anime> listAnimeByNameAndId(String name, long id) {
		return animeRepository.findByNameAndId(name, id);

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

}
