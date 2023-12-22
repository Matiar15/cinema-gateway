package pl.szudor.repertoire

import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szudor.utils.asFilter
import pl.szudor.utils.toDto
import javax.validation.Valid
import javax.validation.constraints.Positive


@RestController
@RequestMapping("/cinema/{cinemaId}/repertoire")
@Validated
class RepertoireController(
    private val repertoireService: RepertoireService,
) {
    @Operation(
        summary = "Create repertoire",
        description = "Create a repertoire."
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid @RequestBody payload: RepertoirePayload,
        @PathVariable @Positive cinemaId: Int,
    ): RepertoireDto = repertoireService.createRepertoire(cinemaId, payload.playedAt!!).toDto()

    @Operation(
        summary = "Get repertoire",
        description = "Get all repertoires with provided filters."
    )
    @GetMapping
    fun index(
        @PathVariable @Positive cinemaId: Int,
        pageRequest: Pageable,
        filter: RepertoireFilterDto,
    ): Page<RepertoireDto> =
        repertoireService.fetchByFilter(cinemaId, filter.asFilter(), pageRequest).map { it.toDto() }

    @Operation(
        summary = "Patch repertoire",
        description = "Patch a repertoire."
    )
    @PatchMapping("/{id}")
    fun patch(
        @PathVariable @Positive cinemaId: Int,
        @PathVariable @Positive id: Int,
        @Valid @RequestBody payload: RepertoirePayload,
    ) = repertoireService.patchRepertoire(id, payload.playedAt!!)
}