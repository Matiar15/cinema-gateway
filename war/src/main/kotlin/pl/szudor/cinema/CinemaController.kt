package pl.szudor.cinema

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
@RequestMapping("/cinema")
@Validated
class CinemaController(
    private val cinemaService: CinemaService
) {
    @Operation(
        summary = "Create a cinema",
        description = "Creates a cinema with provided parameters."
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody payload: CinemaPayload): CinemaDto =
        cinemaService.saveCinema(
            payload.name!!,
            payload.address!!,
            payload.email!!,
            payload.phoneNumber!!,
            payload.postalCode!!,
            payload.director!!,
            payload.nipCode!!,
            payload.buildDate!!
        ).toDto()

    @Operation(
        summary = "Get cinemas",
        description = "Retrieve a paginated cinema list, filters are optional."
    )
    @GetMapping
    fun index(
        @Valid filter: CinemaFilterDto,
        page: Pageable
    ): Page<CinemaDto> = cinemaService.fetchByFilter(page, filter.asFilter()).map { it.toDto() }

    @Operation(
        summary = "Patch cinema",
        description = "Patch cinemas' status."
    )
    @PatchMapping("/{id}")
    fun patch(
        @PathVariable @Positive id: Int,
        @RequestBody @Valid payload: CinemaPatchPayload
    ) = cinemaService.updateState(id, payload.active!!).toDto()
}