package pl.szudor.cinema

import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Positive


@RestController
@RequestMapping("/cinemas")
class CinemaController(
    private val cinemaService: CinemaService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@Validated(CreateValidation::class) @RequestBody cinema: CinemaDto): CinemaDto =
        cinemaService.saveCinema(cinema).toDto()

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): List<CinemaDto> = cinemaService.getCinemas().map { it.toDto() }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable @Positive id: Int, @Validated(UpdateValidation::class) @RequestBody cinema: CinemaDto) =
        cinemaService.updateCinema(id, cinema)

    @PutMapping("/{id}/state")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable @Positive id: Int,
               @RequestBody cinemaPayload: CinemaPayload) = cinemaService.updateState(id, cinemaPayload)
}