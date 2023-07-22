package br.com.animelistor.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.animelistor.domain.Anime;
import br.com.animelistor.dto.AnimeDto;
import br.com.animelistor.services.AnimeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("animes")
public class AnimeController {

	private final AnimeService animeService;

	@Autowired
	AnimeController(AnimeService animeService) {
		this.animeService = animeService;
	}

	@GetMapping
	public ResponseEntity<Page<Anime>> listAnime(Pageable pageable) {
		return ResponseEntity.ok(animeService.listAllAnimes(pageable));
	}
	
	@GetMapping("all")
	public ResponseEntity<List<Anime>> listAnimeNonPageable() {
		return ResponseEntity.ok(animeService.listAllAnimesNonPageable());
	}

	@GetMapping("/findByName")
	public ResponseEntity<List<Anime>> listAnimeByName(@RequestParam String name) {
		return ResponseEntity.ok(animeService.listAnimeByName(name));
	}

	@GetMapping("/findByNameAndId")
	public ResponseEntity<List<Anime>> listAnimeByNameAndId(@RequestParam String name, @RequestParam long id) {
		return ResponseEntity.ok(animeService.listAnimeByNameAndId(name, id));
	}

	@GetMapping("{id}")
	public ResponseEntity<Anime> listAnimeById(@PathVariable long id) {
		return ResponseEntity.ok(animeService.listAnimeByIdOrThrowException(id));

	}

	@PostMapping()
	public ResponseEntity<Anime> createAnime(@RequestBody @Valid AnimeDto anime) {
		return ResponseEntity.status(HttpStatus.CREATED).body(animeService.saveAnime(anime));
	}

	@PutMapping
	public ResponseEntity<Void> replaceAnime(@RequestBody AnimeDto anime) {
		animeService.replaceAnime(anime);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteAnime(@PathVariable long id) {
		animeService.deleteAnime(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

}
