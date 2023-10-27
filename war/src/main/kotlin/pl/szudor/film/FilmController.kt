package pl.szudor.film

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/film")
class FilmController(
    private val filmService: FilmService
) {
    @PostMapping("/repertoire/{repertoireId}/room/{roomId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @PathVariable @Positive repertoireId: Int,
        @PathVariable @Positive roomId: Int,
        @Valid @RequestBody film: FilmDto
    ): FilmDto =
        filmService.saveFilm(film, repertoireId, roomId).toDto()

    @GetMapping
    fun getAll(): List<FilmDto> = filmService.getFilms().map { it.toDto() }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable @Positive id: Int) = filmService.deleteFilm(id)
}