package pl.szudor.repertoire

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/repertoires")
class RepertoireController(
    private val repertoireService: RepertoireService
) {
    @set:Autowired
    lateinit var logger: Logger
    @PostMapping("/{cinemaId}")
    fun saveRepertoire(@RequestBody repertoire: RepertoireDto, @PathVariable cinemaId: Int): RepertoireDto {
        return repertoireService.saveRepertoire(repertoire, cinemaId).toDto()
    }

    @DeleteMapping("/{repertoireId}")
    fun deleteRepertoire(@PathVariable repertoireId: Int) = repertoireService.deleteRepertoire(repertoireId)
}