package pl.szudor.film

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/films")
class FilmController(
    private val filmService: FilmService
) {
    @GetMapping
    fun getFilms(): List<FilmDto>
        = filmService.getFilms().map { it.toDto() }

    @DeleteMapping("/{filmId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteRepertoire(@PathVariable filmId: Int) = filmService.deleteFilm(filmId)
}