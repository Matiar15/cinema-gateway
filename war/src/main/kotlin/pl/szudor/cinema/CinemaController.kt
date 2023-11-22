package pl.szudor.cinema

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Positive


@RestController
@RequestMapping("/cinema")
@Validated
class CinemaController(
    private val cinemaService: CinemaService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Validated(Validation.Create::class) @RequestBody cinema: CinemaDto): CinemaDto =
        cinemaService.saveCinema(cinema).toDto()

    @GetMapping
    fun index(page: Pageable): Page<CinemaDto> =
        cinemaService.getCinemas(page).map { it.toDto() }

    @PutMapping("/{id}")
    fun update(
        @PathVariable @Positive id: Int,
        @Validated(Validation.Update::class) @RequestBody cinema: CinemaDto
    ) =
        cinemaService.updateCinema(id, cinema).toDto()

    @PatchMapping("/{id}")
    fun patch(
        @PathVariable @Positive id: Int,
        @RequestBody @Validated cinemaPayload: CinemaPayload
    ) = cinemaService.updateState(id, cinemaPayload).toDto()

}