package pl.szudor.repertoire

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.szudor.exception.RepertoireNotExistsException
import pl.szudor.film.FilmDto


@RestController
@RequestMapping("/repertoires")
class RepertoireController(
    private val repertoireService: RepertoireService
) {

    @PostMapping("/{cinemaId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveRepertoire(@RequestBody repertoire: RepertoireDto, @PathVariable cinemaId: Int): RepertoireDto
        = repertoireService.saveRepertoire(repertoire, cinemaId).toDto()

    @GetMapping
    fun getRepertoires(): List<RepertoireDto>
            = repertoireService.getRepertoires().map { it.toDto() }

    @DeleteMapping("/{repertoireId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteRepertoire(@PathVariable repertoireId: Int) {
        try {
            return repertoireService.deleteRepertoire(repertoireId)
        } catch (e: RuntimeException) {
            throw RepertoireNotExistsException("Repertoire under ID: $repertoireId was not found.", e)
        }
    }

    @PostMapping("/{repertoireId}/film")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveRepertoire(@RequestBody film: FilmDto, @PathVariable repertoireId: Int): RepertoireDto
            = repertoireService.saveFilmUnderRepertoire(film, repertoireId).toDto()

}