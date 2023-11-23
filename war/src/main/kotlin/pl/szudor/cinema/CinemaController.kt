package pl.szudor.cinema

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szudor.utils.asGRange
import javax.validation.constraints.Positive


@RestController
@RequestMapping("/cinema")
@Validated
class CinemaController(
    private val cinemaService: CinemaService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Validated(Validation.Create::class) @RequestBody cinema: CinemaDto): CinemaDto =
        cinemaService.saveCinema(cinema.toEntity()).toDto()

    @GetMapping
    fun index(
        page: Pageable,
        filter: CinemaFilterDto
    ): Page<CinemaDto> =
        cinemaService.getCinemas(page, filter.asFilter()).map { it.toDto() }

    @PatchMapping("/{id}")
    fun patch(
        @PathVariable @Positive id: Int,
        @RequestBody @Validated cinemaPayload: CinemaPayload
    ) = cinemaService.updateState(id, cinemaPayload.state!!).toDto()
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
        null
        /*createdAt?.asGRange()*/
    )

fun CinemaDto.toEntity() =
    Cinema(
        id = id,
        name = name!!,
        address = address!!,
        email = email!!,
        director = director!!,
        phoneNumber = phoneNumber!!,
        postalCode = postalCode!!,
        nipCode = nipCode!!,
        buildDate = buildDate!!,
        state = state!!
    )


fun Cinema.toDto() =
    CinemaDto(
        id,
        name,
        address,
        email,
        phoneNumber,
        postalCode,
        director,
        nipCode,
        buildDate,
        state,
        createdAt
    )