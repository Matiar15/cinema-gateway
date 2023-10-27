package pl.szudor.cinema

import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Positive


@RestController
@RequestMapping("/cinema")
class CinemaController(
    private val cinemaService: CinemaService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Validated(CreateValidation::class) @RequestBody cinema: CinemaDto): CinemaDto =
        cinemaService.saveCinema(cinema).toDto()

    @GetMapping
    fun index(): List<CinemaDto> = cinemaService.getCinemas().map { it.toDto() }

    @PutMapping("/{id}")
    fun update(@PathVariable @Positive id: Int, @Validated(UpdateValidation::class) @RequestBody cinema: CinemaDto) =
        cinemaService.updateCinema(id, cinema).toDto()

    @PutMapping("/state/{id}")
    fun delete(
        @PathVariable @Positive id: Int,
        @RequestBody @Valid cinemaPayload: CinemaPayload
    ) =
        cinemaService.updateState(id, cinemaPayload).toDto()

}