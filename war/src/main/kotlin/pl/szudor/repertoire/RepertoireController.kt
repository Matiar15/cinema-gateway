package pl.szudor.repertoire

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szudor.cinema.toDto
import pl.szudor.utils.asGRange
import javax.validation.Valid
import javax.validation.constraints.Positive


@RestController
@RequestMapping("/cinema/{cinemaId}/repertoire")
@Validated
class RepertoireController(
    private val repertoireService: RepertoireService,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid @RequestBody payload: RepertoirePayload,
        @PathVariable @Positive cinemaId: Int,
    ): RepertoireDto = repertoireService.createRepertoire(cinemaId, payload.playedAt!!).toDto()

    @GetMapping
    fun index(
        @PathVariable @Positive cinemaId: Int,
        pageRequest: Pageable,
        filter: RepertoireFilterDto,
    ): Page<RepertoireDto> =
        repertoireService.fetchByFilter(cinemaId, filter.asFilter(), pageRequest).map { it.toDto() }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun patch(
        @PathVariable @Positive cinemaId: Int,
        @PathVariable @Positive id: Int,
        @Valid @RequestBody payload: RepertoirePayload,
    ) = repertoireService.patchRepertoire(id, payload.playedAt!!)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable @Positive cinemaId: Int,
        @PathVariable @Positive id: Int,
    ) = repertoireService.deleteRepertoire(id)
}

fun Repertoire.toDto() =
    RepertoireDto(
        id!!,
        playedAt!!,
        cinema!!.toDto(),
        createdAt
    )

fun RepertoireFilterDto.asFilter() = RepertoireFilter(playedAt?.asGRange())