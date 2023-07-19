package br.com.animelistor.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.animelistor.domain.Anime;
import br.com.animelistor.dto.AnimeDto;
import br.com.animelistor.services.AnimeService;


@RestController
@RequestMapping("animes")
public class AnimeController {

	private final AnimeService animeService;

	@Autowired
	AnimeController(AnimeService animeService) {
		this.animeService = animeService;
	}
	
	@GetMapping
	public ResponseEntity<List<Anime>> listAnime (){
		return ResponseEntity.ok(animeService.listAllAnimes());
	
	
	}
	@GetMapping("{id}")
	public ResponseEntity<Anime> listAnimeById (@PathVariable long id){
		return ResponseEntity.status(HttpStatus.OK).body(animeService.listAnimeByIdOrThrowException(id));
	
	}
	
	@PostMapping()
	public ResponseEntity<Anime> createAnime(@RequestBody AnimeDto anime) {
		return ResponseEntity.status(HttpStatus.CREATED).body(animeService.saveAnime(anime));
	}
	
	@PutMapping
	public ResponseEntity<Void> replaceAnime(@RequestBody AnimeDto anime){
		animeService.replaceAnime(anime);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteAnime(@PathVariable long id){
		 animeService.deleteAnime(id);
		 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
	}

}
