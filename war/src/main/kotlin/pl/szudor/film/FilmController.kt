package pl.szudor.film

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/films")
class FilmController(
    private val filmService: FilmService
) {
    @PostMapping("/{repertoireId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun postFilm(@RequestBody @Valid film: FilmDto, @PathVariable repertoireId: Int): FilmDto
        = filmService.saveFilm(film, repertoireId).toDto()

    @GetMapping
    fun getFilms(): List<FilmDto>
        = filmService.getFilms().map { it.toDto() }

    @DeleteMapping("/{filmId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteRepertoire(@PathVariable filmId: Int) = filmService.deleteFilm(filmId)
}