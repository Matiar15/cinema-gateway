package pl.szudor.cinema

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/cinemas")
class CinemaController (
    private val cinemaService: CinemaService,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postCinema(@RequestBody @Valid cinema: CinemaDto): Cinema
            = cinemaService.storeCinema(cinema)

    @GetMapping
    fun getCinemas(): List<Cinema> = cinemaService.getAllCinemas()


}