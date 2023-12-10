package pl.szudor.repertoire

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Positive


@RestController
@RequestMapping("/repertoire")
class RepertoireController(
    private val repertoireService: RepertoireService
) {

    @PostMapping("/cinema/{cinemaId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody repertoire: RepertoireDto, @PathVariable @Positive cinemaId: Int): RepertoireDto
        = repertoireService.saveRepertoire(repertoire, cinemaId).toDto()

    @GetMapping
    fun getAll(): List<RepertoireDto>
            = repertoireService.getRepertoires().map { it.toDto() }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable @Positive id: Int) = repertoireService.deleteRepertoire(id)

}