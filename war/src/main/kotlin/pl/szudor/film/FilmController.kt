/*
package pl.szudor.film

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szudor.repertoire.toDto
import pl.szudor.room.toDto
import javax.validation.Valid
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/film")
class FilmController(
    private val filmService: FilmService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
    ): FilmDto =
        filmService.saveFilm(payload.toPayloadDto()).toDto()
    @GetMapping
    fun index(page: Pageable): Page<FilmDto> = filmService.getAll(page).map { it.toDto() }

    @PutMapping("/{id}")
    fun update(
        @PathVariable @Positive id: Int,
        @Validated(UpdateValidation::class) @RequestBody filter: FilmFilterDto
    ) = filmService.updateFilm(id, filter.asFilter()).toDto()

    @PatchMapping("/{id}")
    fun patch(
        @PathVariable @Positive id: Int,
        @Validated(PatchValidation::class) @RequestBody filter: FilmFilterDto
    ) = filmService.patchFilm(id, filter.asFilter()).toDto()

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable @Positive id: Int) = filmService.deleteFilm(id)
}
fun Film.toDto() = FilmDto(
    id, playedAt, repertoire?.toDto(), title, pegi, duration, releaseDate, originalLanguage, room?.toDto(), createdAt
)
fun FilmFilterDto.asFilter() = FilmFilter(
    repertoire.id, room.id, playedAt, title, pegi, duration, releaseDate, originalLanguage, createdAt
)*/
