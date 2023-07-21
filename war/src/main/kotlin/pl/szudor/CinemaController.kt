package pl.szudor

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.szudor.cinema.Cinema
import pl.szudor.cinema.CinemaDto
import pl.szudor.cinema.CinemaService
import pl.szudor.cinema.toCinema
import javax.validation.Valid
@RestController
@RequestMapping("/cinemas")
class CinemaController (
    private val cinemaService: CinemaService,
) {
    @PostMapping
    fun postCinema(@RequestBody @Valid cinema: CinemaDto): ResponseEntity<Cinema> {
        val toCinema = cinema.toCinema()
        cinemaService.storeCinema(toCinema)
        return ResponseEntity(toCinema, HttpStatus.CREATED)
    }
    @GetMapping
    fun getCinemas(): List<Cinema> {
        return cinemaService.getAllCinemas()
    }

}