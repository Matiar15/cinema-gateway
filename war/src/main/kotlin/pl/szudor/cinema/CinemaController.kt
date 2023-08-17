package pl.szudor.cinema

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.szudor.repertoire.toDto
import javax.validation.Valid


@RestController
@RequestMapping("/cinemas")
class CinemaController (
    private val cinemaService: CinemaService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postCinema(@RequestBody @Valid cinema: CinemaDto): CinemaDto {
        return cinemaService.storeCinema(cinema).toDto()
    }


    @GetMapping
    fun getCinemas(): List<CinemaDto> = cinemaService.getAllCinemas().map { it.toDto() }


}