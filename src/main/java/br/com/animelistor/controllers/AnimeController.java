package br.com.animelistor.controllers;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/animes")
public class AnimeController {

	private final AnimeService animeService;

	@Autowired
	AnimeController(AnimeService animeService) {
		this.animeService = animeService;
	}

	@GetMapping
	@Operation(summary = "List all animes with pagination", description = "5 animes per page by default, use the parameter to change it.")
	public ResponseEntity<Page<Anime>> listAnime(@ParameterObject Pageable pageable) {
		return ResponseEntity.ok(animeService.listAllAnimes(pageable));
	}
	
	
	@Operation(summary = "List all animes without pagination")
	@GetMapping("all")
	public ResponseEntity<List<Anime>> listAnimeNonPageable() {
		return ResponseEntity.ok(animeService.listAllAnimesNonPageable());
	}
	
	
	@Operation(summary = "Find anime by name and return a list. The list will be empty if the name doesn't exist in database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operation successful"),
	})
	@GetMapping("/findByName")
	public ResponseEntity<List<Anime>> listAnimeByName(@RequestParam String name) {
		return ResponseEntity.ok(animeService.listAnimeByName(name));
	}

	
	@Operation(summary = "Find anime by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operation successful."),
			@ApiResponse(responseCode = "400", description = "When anime doesn't exist in data base.")
	})
	@GetMapping("{id}")
	public ResponseEntity<Anime> listAnimeById(@PathVariable long id) {
		return ResponseEntity.ok(animeService.listAnimeByIdOrThrowException(id));

	}
	
	
	@Operation(summary = "Create a new anime")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Operation successful.")
	})
	@PostMapping()
	public ResponseEntity<Anime> createAnime(@RequestBody @Valid AnimeDto anime) {
		return ResponseEntity.status(HttpStatus.CREATED).body(animeService.saveAnime(anime));
	}
	
	
	@Operation(summary = "Updates a existing anime")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Operation successful."),
			@ApiResponse(responseCode = "400", description = "When anime doesn't exist in data base.")
	})
	@PutMapping
	public ResponseEntity<Void> replaceAnime(@RequestBody AnimeDto anime) {
		animeService.replaceAnime(anime);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	

	@Operation(summary = "Deletes a existing anime")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Operation successful."),
			@ApiResponse(responseCode = "400", description = "When anime doesn't exist in data base.")
	})
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteAnime(@PathVariable long id) {
		animeService.deleteAnime(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

}
