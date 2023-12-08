package pl.szudor.film

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szudor.utils.asGRange
import javax.validation.Valid
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/film")
@Validated
class FilmController(
    private val filmService: FilmService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody @Valid payload: FilmPayload
    ): FilmDto = filmService.saveFilm(
        payload.playedAt!!,
        payload.title!!,
        payload.pegi!!,
        payload.duration!!,
        payload.releaseDate!!,
        payload.originalLanguage!!
    ).toDto()

    @GetMapping
    fun index(filter: FilmFilterDto, page: Pageable): Page<FilmDto> =
        filmService.fetchByFilter(filter.asFilter(), page).map { it.toDto() }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable @Positive id: Int) = filmService.deleteFilm(id)
}

fun Film.toDto() = FilmDto(
    id!!, playedAt!!, title!!, pegi!!, duration!!, releaseDate!!, originalLanguage!!, createdAt!!
)

fun FilmFilterDto.asFilter() = FilmFilter(
    playedAt?.asGRange(),
    title,
    pegi,
    duration?.asGRange(),
    releaseDate?.asGRange(),
    originalLanguage,
    createdAt?.asGRange()
)
