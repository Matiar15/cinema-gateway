package pl.szudor.repertoire

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.szudor.film.FilmDto
import pl.szudor.film.toDto

@RestController
@RequestMapping("/repertoires")
class RepertoireController(
    private val repertoireService: RepertoireService
) {
    @set:Autowired
    lateinit var logger: Logger
    @PostMapping("/{cinemaId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveRepertoire(@RequestBody repertoire: RepertoireDto, @PathVariable cinemaId: Int): RepertoireDto
        = repertoireService.saveRepertoire(repertoire, cinemaId).toDto()

    @GetMapping
    fun getRepertoires(): List<RepertoireDto>
            = repertoireService.getRepertoires().map { it.toDto() }

    @DeleteMapping("/{repertoireId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteRepertoire(@PathVariable repertoireId: Int) = repertoireService.deleteRepertoire(repertoireId)
}