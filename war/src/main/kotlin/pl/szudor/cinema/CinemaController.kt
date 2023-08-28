package pl.szudor.cinema

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/cinemas")
class CinemaController (
    private val cinemaService: CinemaService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postCinema(@RequestBody @Valid cinema: CinemaDto): CinemaDto {
        return cinemaService.saveCinema(cinema).toDto()
    }

    @GetMapping
    fun getCinemas(): List<CinemaDto> = cinemaService.getCinemas().map { it.toDto() }

    @DeleteMapping("/{cinemaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCinema(@PathVariable cinemaId: Int) = cinemaService.deleteCinema(cinemaId)
}