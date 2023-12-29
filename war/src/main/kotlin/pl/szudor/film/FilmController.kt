package pl.szudor.film

import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szudor.utils.asFilter
import pl.szudor.utils.toDto
import javax.validation.Valid
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/film")
@Validated
class FilmController(
    private val filmService: FilmService
) {
    @Operation(
        summary = "Create film",
        description = "Creates a new film."
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody @Valid payload: FilmPayload
    ): FilmDto = filmService.saveFilm(
        payload.title!!,
        payload.pegi!!,
        payload.duration!!,
        payload.releaseDate!!,
        payload.originalLanguage!!
    ).toDto()

    @Operation(
        summary = "Get films",
        description = "Gets all films with provided filters."
    )
    @GetMapping
    fun index(filter: FilmFilterDto, page: Pageable): Page<FilmDto> =
        filmService.fetchByFilter(filter.asFilter(), page).map { it.toDto() }

    @Operation(
        summary = "Delete film",
        description = "Deletes a film and all events associated with this film."
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable @Positive id: Int) = filmService.deleteFilm(id)
}