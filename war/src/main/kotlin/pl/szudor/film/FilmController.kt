package pl.szudor.film

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/films")
class FilmController(
    private val filmService: FilmService
) {
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun postFilm(@RequestBody @Valid film: FilmDto): FilmDto
        = filmService.saveFilm(film).toDto()

}