package pl.szudor.seat

import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/room/{roomId}/seat")
@Validated
class SeatController(
    private val seatService: SeatService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @PathVariable @Positive roomId: Int,
        @Valid @RequestBody payload: SeatPostPayload
    ) = seatService.saveSeat(roomId, payload.number!!).toDto()

    @PatchMapping("/{id}")
    fun patch(
        @PathVariable @Positive roomId: Int,
        @PathVariable @Positive id: Int,
        @Valid @RequestBody payload: SeatPatchPayload
    ) = seatService.patchSeat(id, payload.occupied!!).toDto()

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable @Positive roomId: Int,
        @PathVariable @Positive id: Int
    ) = seatService.deleteSeat(id)
}

fun Seat.toDto() = SeatDto(
    id!!,
    number!!,
    occupied!!
)