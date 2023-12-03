package pl.szudor.cinema

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szudor.utils.asGRange
import javax.validation.Valid
import javax.validation.constraints.Positive


@RestController
@RequestMapping("/cinema")
@Validated
class CinemaController(
    private val cinemaService: CinemaService
) {
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

    @GetMapping
    fun index(
        @Validated filter: CinemaFilterDto,
        page: Pageable
    ): Page<CinemaDto> =
        cinemaService.getCinemas(page, filter.asFilter()).map { it.toDto() }

    @PatchMapping("/{id}")
    fun patch(
        @PathVariable @Positive id: Int,
        @RequestBody @Validated payload: CinemaPatchPayload
    ) = cinemaService.updateState(id, payload.state!!).toDto()
}

fun CinemaFilterDto.asFilter() =
    CinemaFilter(
        name,
        address,
        email,
        phoneNumber,
        postalCode,
        director,
        nipCode,
        buildDate?.asGRange(),
        state,
        createdAt?.asGRange()
    )

fun Cinema.toDto() =
    CinemaDto(
        id!!,
        name!!,
        address!!,
        email!!,
        phoneNumber!!,
        postalCode!!,
        director!!,
        nipCode!!,
        buildDate!!,
        state!!,
        createdAt!!
    )