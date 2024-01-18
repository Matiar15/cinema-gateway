package pl.szudor.seat.reserved

import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szudor.seat.SeatDto
import pl.szudor.utils.toDto
import javax.validation.Valid
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/event/{eventId}/seat")
@Validated
class ReservedSeatController(
    private val reservedSeatService: ReservedSeatService,
) {
    @Operation(
        summary = "Reserve seat", description = "Reserves seat in event context."
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@PathVariable @Positive eventId: Int, @RequestBody @Valid payload: ReservedSeatPayload) =
        reservedSeatService.create(eventId, payload.id!!).seat?.toDto()

    @Operation(
        summary = "Get all reservations",
        description = "Returns a paged list of all reserved seats under provided event ID."
    )
    @GetMapping
    fun index(
        @PathVariable @Positive eventId: Int,
        pageRequest: Pageable,
    ): Page<SeatDto> = reservedSeatService.fetch(eventId, pageRequest).map { it.toDto() }
    @Operation(
        summary = "Delete reservation", description = "Deletes reservation for seat with given ID"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable @Positive eventId: Int, @PathVariable @Positive id: Int) =
        reservedSeatService.delete(id)
}